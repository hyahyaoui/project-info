package example.tasks;

import com.example.artifacts.inspector.commons.model.ApplicationArtifactsInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.enhancers.*;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;

public class ApplicationArtifactsInspector extends DefaultTask {

    @Input
    private String k8sPodYamlPath = "pipeline/KubernetesPod.yaml"; // Default path
    @Internal
    private final String BUILD_INFO_FILE_NAME = "build-info.json";

    @Input
    @Optional
    private String metaInfFolderPath = "resources/main"; // Default path

    @TaskAction
    public void collectBuildInfo() {
        ArtifactInfoEnhancer enhancer = initEnhancers();
        ApplicationArtifactsInfo applicationArtifactsInfo = new ApplicationArtifactsInfo();
        while (enhancer != null) {
            try {
                getLogger().quiet("Running enhancer " + enhancer.getClass().getSimpleName());
                enhancer.enhance(getProject(), applicationArtifactsInfo);
                enhancer = enhancer.getNextNextEnhancer();
            } catch (Exception e) {
                getLogger().error("Error while executing enhancer + enhancerName, e");
            }
        }
        ;
        getLogger().quiet("Collecting Project Information Done!");
        writeCollectedInfoToFile(applicationArtifactsInfo);
    }

    private ArtifactInfoEnhancer initEnhancers() {

        ArtifactInfoEnhancer gradleInfoEnhancer = new GradleInfoEnhancer();
        gradleInfoEnhancer.setNextEnhancer(new JavaInfoEnhancer())
                .setNextEnhancer(new JavaDependenciesInfoEnhancer())
                .setNextEnhancer(new JenkinsBuildImagesInfoEnhancer(this.k8sPodYamlPath));

        return gradleInfoEnhancer;
    }

    private void writeCollectedInfoToFile(ApplicationArtifactsInfo applicationArtifactsInfo) {
        getLogger().quiet("Generating build-info file");
        try {
            File resourcesDir = getProject().getProjectDir();
// Ensure META-INF directory exists
            File metaInfDirectory = new File(resourcesDir, metaInfFolderPath);
            if (!metaInfDirectory.exists()) {
                getLogger().info("Creating META-INF directory...");
                if (!metaInfDirectory.mkdirs()) {
                    getLogger().warn("Failed to create META-INF directory");
                    return;
                }
            }
            File outputJsonFile = new File(resourcesDir, metaInfFolderPath);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(outputJsonFile, applicationArtifactsInfo);
            getLogger().quiet("Collected info written to file: " + outputJsonFile);
        } catch (IOException e) {
            getLogger().warn("Failed to write buid info to file", e);
            getLogger().quiet("Build info file generated successfully");
        }


    }

    public String getK8sPodYamlPath() {
        return k8sPodYamlPath;
    }

    public void setK8sPodYamlPath(String k8sPodYamlPath) {
        this.k8sPodYamlPath = k8sPodYamlPath;
    }

    public String getBUILD_INFO_FILE_NAME() {
        return BUILD_INFO_FILE_NAME;
    }

    public String getMetaInfFolderPath() {
        return metaInfFolderPath;
    }

    public void setMetaInfFolderPath(String metaInfFolderPath) {
        this.metaInfFolderPath = metaInfFolderPath;
    }
}
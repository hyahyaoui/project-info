package example.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.collectors.*;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProjectArtifactsInfoTask extends DefaultTask {
    @Input
    @Optional
    Set<String> artifactsToBeScaned;

    @Input
    @Optional
    String k8sPodYamlPath = "pipeline/KubernetesPod.yaml"; // Default path
    @Internal
    private final String outputJsonFilePath = "/resources/main/META-INF/project-info.json";
    @Internal
    Map<String, ArtifactInfoCollectorFunction> collectors = new HashMap<>();

    @TaskAction
    void collectProjectInfo() {
        initCollectors();

        Map<String, ArtifactInfo> collectedInfo = new HashMap<>();

        getLogger().quiet("Collecting Project Information...");
        collectors.forEach((collectorName, collectorFunction) -> {
            getLogger().quiet("Running collector: " + collectorName);
            ArtifactInfo artifactInfo = collectorFunction.getInfo(getProject());
            getLogger().quiet("Executed collector: " + collectorName);
            collectedInfo.put(collectorName, artifactInfo);
        });

        writeCollectedInfoToFile(collectedInfo);
    }


    private void initCollectors() {
        collectors.put("javaInfo", new JavaInfoCollector());
        collectors.put("gradleInfo", new GradleInfoCollector());
        collectors.put("dependenciesInfo", new JavaDependenciesCollector());

        getLogger().quiet("Given artifacts to scan" + artifactsToBeScaned);

        if (artifactsToBeScaned != null && !artifactsToBeScaned.isEmpty()) {
            collectors.keySet().retainAll(artifactsToBeScaned);
        }
    }

    private void writeCollectedInfoToFile(Map<String, ArtifactInfo> collectedInfo) {
        try {
            File resourcesDir = getProject().getBuildDir();
            // Ensure META-INF directory exists
            File metaInfDirectory = new File(resourcesDir, "resources/main/META-INF");
            if (!metaInfDirectory.exists()) {
                getLogger().info("Creating META-INF directory ...");
                if (!metaInfDirectory.mkdirs()) {
                    getLogger().warn("Failed to create META-INF directory");
                    return;
                }
            }

            File outputJsonFile = new File(resourcesDir, outputJsonFilePath);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(outputJsonFile, collectedInfo);
            getLogger().quiet("Collected info written to file: " + outputJsonFile.getAbsolutePath());
        } catch (IOException e) {
            getLogger().warn("Failed to write collected info to file", e);
        }
    }

    public Set<String> getArtifactsToBeScaned() {
        return artifactsToBeScaned;
    }

    public void setArtifactsToBeScaned(Set<String> artifactsToBeScaned) {
        getLogger().info("setting to " + artifactsToBeScaned);
        this.artifactsToBeScaned = artifactsToBeScaned;
    }

    public String getK8sPodYamlPath() {
        return k8sPodYamlPath;
    }

    public void setK8sPodYamlPath(String k8sPodYamlPath) {
        this.k8sPodYamlPath = k8sPodYamlPath;
    }

    public String getOutputJsonFilePath() {
        return outputJsonFilePath;
    }

    public Map<String, ArtifactInfoCollectorFunction> getCollectors() {
        return collectors;
    }

    public void setCollectors(Map<String, ArtifactInfoCollectorFunction> collectors) {
        this.collectors = collectors;
    }


}

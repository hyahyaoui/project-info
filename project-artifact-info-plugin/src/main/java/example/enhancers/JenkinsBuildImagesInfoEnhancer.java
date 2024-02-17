package example.enhancers;

import com.example.artifacts.inspector.commons.model.ApplicationArtifactsInfo;
import com.example.artifacts.inspector.commons.model.ContainerImageInfo;
import org.gradle.api.Project;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class JenkinsBuildImagesInfoEnhancer implements ArtifactInfoEnhancer {
    private final String kubernetesYamlFilePath;
    private ArtifactInfoEnhancer nextEnhancer;

    public JenkinsBuildImagesInfoEnhancer(String kubernetesYamlFilePath) {
        this.kubernetesYamlFilePath = kubernetesYamlFilePath;
    }

    @Override
    public void enhance(Project project, ApplicationArtifactsInfo applicationArtifactsInfo) {
        try {
            final File kubernetesPodFile = new File(project.getProjectDir(), kubernetesYamlFilePath).getCanonicalFile();
            extractImages(kubernetesPodFile, applicationArtifactsInfo);
        } catch (IOException e) {
            project.getLogger().error("Error while reading Kubernetes Pod file: {}", e.getMessage());
        }

        if (nextEnhancer != null) {
            nextEnhancer.enhance(project, applicationArtifactsInfo);
        }
    }

    private void extractImages(File kubernetesPodFile, ApplicationArtifactsInfo applicationArtifactsInfo) {
        try (FileInputStream fis = new FileInputStream(kubernetesPodFile)) {
            Yaml yaml = new Yaml();
            Map<String, Object> yamlData = yaml.load(fis);

            if (yamlData != null && yamlData.containsKey("spec")) {
                Map<String, Object> spec = (Map<String, Object>) yamlData.get("spec");

                if (spec != null && spec.containsKey("containers")) {
                    processContainers(spec.get("containers"), applicationArtifactsInfo);
                }
            }
        } catch (IOException | RuntimeException e) {
        }
    }

    private void processContainers(Object containers, ApplicationArtifactsInfo applicationArtifactsInfo) {
        if (containers instanceof Iterable) {
            for (Map<String, Object> container : (Iterable<Map<String, Object>>) containers) {
                processContainer(container, applicationArtifactsInfo);
            }
        }
    }

    private void processContainer(Map<String, Object> container, ApplicationArtifactsInfo applicationArtifactsInfo) {
        if (container.containsKey("name") && container.containsKey("image")) {
            String name = container.get("name").toString();
            String version = extractImageVersion(container.get("image").toString());
            String namespace = extractImageNameSpace(container.get("image").toString());

            ContainerImageInfo containerImage = new ContainerImageInfo(name, version, namespace);
            applicationArtifactsInfo.getBuildInformation().getJenkinsImages().put(name, containerImage);
        }
    }

    private String extractImageNameSpace(String image) {
        String namespace = "Unknown";
        if (image != null) {
            String[] split = image.split("/");
            namespace = (split.length > 1) ? split[split.length - 2] : "Unknown";
        }
        return namespace;
    }

    private String extractImageVersion(String image) {
        int lastColonIndex = image.lastIndexOf(':');
        return (lastColonIndex != -1) ? image.substring(lastColonIndex + 1) : "Unknown";
    }

    @Override
    public ArtifactInfoEnhancer setNextEnhancer(ArtifactInfoEnhancer nextEnhancer) {
        this.nextEnhancer = nextEnhancer;
        return this;
    }

    @Override
    public ArtifactInfoEnhancer getNextNextEnhancer() {
        return nextEnhancer;
    }
}

package example.collectors;

import org.gradle.api.Project;
import org.gradle.internal.impldep.org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class KubernetesInfoCollector implements ArtifactInfoCollectorFunction {

    private String kubernetesPodFilePath = "pipeline/KubernetesPod.yaml"; // Default path

    @Override
    public KubernetesInfo getInfo(Project project) {
        File kubernetesPodFile = new File(project.getProjectDir(), kubernetesPodFilePath);
        Map<String, String> images = parseKubernetesPodFile(kubernetesPodFile);

        return new KubernetesInfo(images);
    }

    private Map<String, String> parseKubernetesPodFile(File kubernetesPodFile) {
        Map<String, String> images = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(kubernetesPodFile)) {
            Yaml yaml = new Yaml();
            Map<String, Object> yamlData = yaml.load(fis);

            handleYamlData(images, yamlData);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return images;
    }

    private void handleYamlData(Map<String, String> images, Map<String, Object> yamlData) {
        if (yamlData.containsKey("spec")) {
            Map<String, Object> spec = (Map<String, Object>) yamlData.get("spec");
            if (spec.containsKey("containers")) {
                for (Map<String, Object> container : (Iterable<Map<String, Object>>) spec.get("containers")) {
                    if (container.containsKey("name") && container.containsKey("image")) {
                        String imageName = container.get("name").toString();
                        String imageVersion = extractImageVersion(container.get("image").toString());
                        images.put(imageName, imageVersion);
                    }
                }
            }
        }
    }

    private String extractImageVersion(String fullImageName) {
        // Implement logic to extract the version from the full image name
        // Example: parse the version from "registry/image:v1.0"

        // For demonstration purposes, a dummy version is extracted using substring.
        int lastColonIndex = fullImageName.lastIndexOf(':');
        return lastColonIndex != -1 ? fullImageName.substring(lastColonIndex + 1) : "Unknown";
    }

    public static class KubernetesInfo implements ArtifactInfo {
        private Map<String, String> images;

        public KubernetesInfo(Map<String, String> images) {
            this.images = images;
        }

        public Map<String, String> getImages() {
            return images;
        }
    }
}

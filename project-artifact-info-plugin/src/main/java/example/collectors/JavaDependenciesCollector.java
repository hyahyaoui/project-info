package example.collectors;

import org.gradle.api.Project;

import java.util.HashMap;
import java.util.Map;

public class JavaDependenciesCollector implements ArtifactInfoCollectorFunction {
    private static final String RUNTIME_CLASSPATH_CONFIGURATION = "runtimeClasspath";

    @Override
    public ArtifactInfo getInfo(Project project) {
        final DependenciesInfo dependenciesInfo = new DependenciesInfo();

        project.getConfigurations()
                .getByName(RUNTIME_CLASSPATH_CONFIGURATION)
                .getFiles().forEach(file -> {
            String[] parts = file.getAbsolutePath().split("\\\\");
            if (parts.length >= 5) {
                String artifactId = parts[parts.length - 4];
                String version = parts[parts.length - 3];

                dependenciesInfo.getDependencies().put(artifactId, version);
            }
        });

        return dependenciesInfo;
    }

    private static class DependenciesInfo implements ArtifactInfo {
        private Map<String, String> dependencies = new HashMap<>();

        public Map<String, String> getDependencies() {
            return dependencies;
        }
    }
}

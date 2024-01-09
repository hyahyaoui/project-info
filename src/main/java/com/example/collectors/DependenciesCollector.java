package com.example.collectors;

import org.gradle.api.Project;

import java.util.HashMap;
import java.util.Map;

public class DependenciesCollector implements ArtifactInfoCollectorFunction {
    @Override
    public ArtifactInfo getInfo(Project project) {
        final DependenciesInfo dependenciesInfo = new DependenciesInfo();

        project.getConfigurations()
                .getByName("runtimeClasspath")
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

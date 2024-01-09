package com.example.collectors;

import org.gradle.api.Project;

public class GradleInfoCollector implements ArtifactInfoCollectorFunction {
    @Override
    public GradleInfo getInfo(Project project) {
        String gradleVersion = project.getGradle().getGradleVersion();
        return new GradleInfo(gradleVersion);
    }

    public static class GradleInfo implements ArtifactInfo {
        private String version;

        public GradleInfo(String version) {
            this.version = version;
        }

        public String getVersion() {
            return version;
        }

      }
}

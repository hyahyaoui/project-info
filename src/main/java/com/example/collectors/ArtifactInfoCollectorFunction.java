package com.example.collectors;

import org.gradle.api.Project;

@FunctionalInterface
public interface ArtifactInfoCollectorFunction {
    ArtifactInfo getInfo(Project project);
}

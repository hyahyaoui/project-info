package com.example.artifacts.inspector.commons.model;

import java.util.HashMap;
import java.util.Map;

public class BuildInformation {
    private String gradleVersion;
    private Map<String, ContainerImageInfo> jenkinsImages = new HashMap<>();
    private Map<String, JavaDependency> javaDependencies = new HashMap<>();
    private String javaVersion;

    public String getGradleVersion() {
        return gradleVersion;
    }

    public void setGradleVersion(String gradleVersion) {
        this.gradleVersion = gradleVersion;
    }

    public Map<String, ContainerImageInfo> getJenkinsImages() {
        return jenkinsImages;
    }

    public void setJenkinsImages(Map<String, ContainerImageInfo> jenkinsImages) {
        this.jenkinsImages = jenkinsImages;
    }

    public Map<String, JavaDependency> getJavaDependencies() {
        return javaDependencies;
    }

    public void setJavaDependencies(Map<String, JavaDependency> javaDependencies) {
        this.javaDependencies = javaDependencies;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }
}

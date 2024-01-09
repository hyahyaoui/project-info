package com.example.extension;

import java.util.ArrayList;
import java.util.List;

public class ProjectArtifactsInfoPluginExtension {
    private List<String> artifacts = new ArrayList<>();

    public List<String> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<String> artifacts) {
        this.artifacts = artifacts;
    }
}

package com.example.artifacts.inspector.commons.model;

import java.util.HashSet;
import java.util.Set;

public class ApplicationArtifactsInfo {
    private long versionTimestamp = System.currentTimeMillis();
    private BuildInformation buildInformation = new BuildInformation();
    private Set<KubernetesObject> kubernetesObjects = new HashSet<>();

    public long getVersionTimestamp() {
        return versionTimestamp;
    }

    public void setVersionTimestamp(long versionTimestamp) {
        this.versionTimestamp = versionTimestamp;
    }

    public BuildInformation getBuildInformation() {
        return buildInformation;
    }

    public void setBuildInformation(BuildInformation buildInformation) {
        this.buildInformation = buildInformation;
    }

    public Set<KubernetesObject> getKubernetesObjects() {
        return kubernetesObjects;
    }

    public void setKubernetesObjects(Set<KubernetesObject> kubernetesObjects) {
        this.kubernetesObjects = kubernetesObjects;
    }
}

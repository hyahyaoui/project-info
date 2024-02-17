package com.example.artifacts.inspector.model.search.result;

import com.example.artifacts.inspector.commons.model.BuildInformation;
import com.example.artifacts.inspector.commons.model.KubernetesObject;

import java.util.List;

public class ArtifactResult {

    private String applicationName;
    private String environment;
    private String versionTimestamp;
    private BuildInformation buildInformation;
    private List<KubernetesObject> kubernetesObjects;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getVersionTimestamp() {
        return versionTimestamp;
    }

    public void setVersionTimestamp(String versionTimestamp) {
        this.versionTimestamp = versionTimestamp;
    }

    public BuildInformation getBuildInformation() {
        return buildInformation;
    }

    public void setBuildInformation(BuildInformation buildInformation) {
        this.buildInformation = buildInformation;
    }

    public List<KubernetesObject> getKubernetesObjects() {
        return kubernetesObjects;
    }

    public void setKubernetesObjects(List<KubernetesObject> kubernetesObjects) {
        this.kubernetesObjects = kubernetesObjects;
    }
}

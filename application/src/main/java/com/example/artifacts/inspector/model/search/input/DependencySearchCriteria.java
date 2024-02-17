package com.example.artifacts.inspector.model.search.input;

public class DependencySearchCriteria {

    private String groupId;
    private String artifactId;
    private Criteria version;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public Criteria getVersion() {
        return version;
    }

    public void setVersion(Criteria version) {
        this.version = version;
    }
}

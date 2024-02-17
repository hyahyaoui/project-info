package com.example.artifacts.inspector.model.search.input;

public class KubernetesObjectSearchCriteria {

    private String name;
    private Criteria version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Criteria getVersion() {
        return version;
    }

    public void setVersion(Criteria version) {
        this.version = version;
    }
}

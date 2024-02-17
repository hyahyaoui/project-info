package com.example.artifacts.inspector.model.search.input;

public class ImageSearchCriteria {

    private String name;
    private Criteria version;
    private Criteria namespace;

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

    public Criteria getNamespace() {
        return namespace;
    }

    public void setNamespace(Criteria namespace) {
        this.namespace = namespace;
    }
}

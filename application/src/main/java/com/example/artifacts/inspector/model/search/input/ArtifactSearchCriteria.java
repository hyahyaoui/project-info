package com.example.artifacts.inspector.model.search.input;

import java.util.List;

public class ArtifactSearchCriteria {
    private List<Criteria> gradleVersionCriteriaList;
    private List<ImageSearchCriteria> imageCriteriaList;
    private List<DependencySearchCriteria> dependencyCriteriaList;
    private List<Criteria> javaVersionCriteriaList;
    private List<KubernetesObjectSearchCriteria> kubernetesObjectCriteriaList;

    public List<Criteria> getGradleVersionCriteriaList() {
        return gradleVersionCriteriaList;
    }

    public void setGradleVersionCriteriaList(List<Criteria> gradleVersionCriteriaList) {
        this.gradleVersionCriteriaList = gradleVersionCriteriaList;
    }

    public List<ImageSearchCriteria> getImageCriteriaList() {
        return imageCriteriaList;
    }

    public void setImageCriteriaList(List<ImageSearchCriteria> imageCriteriaList) {
        this.imageCriteriaList = imageCriteriaList;
    }

    public List<DependencySearchCriteria> getDependencyCriteriaList() {
        return dependencyCriteriaList;
    }

    public void setDependencyCriteriaList(List<DependencySearchCriteria> dependencyCriteriaList) {
        this.dependencyCriteriaList = dependencyCriteriaList;
    }

    public List<Criteria> getJavaVersionCriteriaList() {
        return javaVersionCriteriaList;
    }

    public void setJavaVersionCriteriaList(List<Criteria> javaVersionCriteriaList) {
        this.javaVersionCriteriaList = javaVersionCriteriaList;
    }

    public List<KubernetesObjectSearchCriteria> getKubernetesObjectCriteriaList() {
        return kubernetesObjectCriteriaList;
    }

    public void setKubernetesObjectCriteriaList(List<KubernetesObjectSearchCriteria> kubernetesObjectCriteriaList) {
        this.kubernetesObjectCriteriaList = kubernetesObjectCriteriaList;
    }
}

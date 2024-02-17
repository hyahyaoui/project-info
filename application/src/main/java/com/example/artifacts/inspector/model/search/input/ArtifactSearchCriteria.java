package com.example.artifacts.inspector.model.search.input;

public class ArtifactSearchCriteria {

    private Criteria versionTimestamp;
    private Criteria gradleVersion;
    private ImageSearchCriteria imageCriteria;
    private DependencySearchCriteria DependencyCriteria;
    private Criteria JavaVersionCriteria;
    private KubernetesObjectSearchCriteria kubernetesObjectCriteria;

    public Criteria getVersionTimestamp() {
        return versionTimestamp;
    }

    public void setVersionTimestamp(Criteria versionTimestamp) {
        this.versionTimestamp = versionTimestamp;
    }

    public Criteria getGradleVersion() {
        return gradleVersion;
    }

    public void setGradleVersion(Criteria gradleVersion) {
        this.gradleVersion = gradleVersion;
    }

    public ImageSearchCriteria getImageCriteria() {
        return imageCriteria;
    }

    public void setImageCriteria(ImageSearchCriteria imageCriteria) {
        this.imageCriteria = imageCriteria;
    }

    public DependencySearchCriteria getDependencyCriteria() {
        return DependencyCriteria;
    }

    public void setDependencyCriteria(DependencySearchCriteria dependencyCriteria) {
        this.DependencyCriteria = dependencyCriteria;
    }

    public Criteria getJavaVersionCriteria() {
        return JavaVersionCriteria;
    }

    public void setJavaVersionCriteria(Criteria javaVersionCriteria) {
        this.JavaVersionCriteria = javaVersionCriteria;
    }

    public KubernetesObjectSearchCriteria getKubernetesObjectCriteria() {
        return kubernetesObjectCriteria;
    }

    public void setKubernetesObjectCriteria(KubernetesObjectSearchCriteria kubernetesObjectCriteria) {
        this.kubernetesObjectCriteria = kubernetesObjectCriteria;
    }
}

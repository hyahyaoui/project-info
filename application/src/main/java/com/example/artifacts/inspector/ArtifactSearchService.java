package com.example.artifacts.inspector;

import com.example.artifacts.inspector.commons.model.ContainerImageInfo;
import com.example.artifacts.inspector.commons.model.JavaDependency;
import com.example.artifacts.inspector.commons.model.KubernetesObject;
import com.example.artifacts.inspector.configuration.EnvironmentConfig;
import com.example.artifacts.inspector.model.search.input.*;
import com.example.artifacts.inspector.model.search.result.ArtifactResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArtifactSearchService {

    private final EnvironmentConfig environmentConfig;

    public ArtifactSearchService(EnvironmentConfig environmentConfig) {
        this.environmentConfig = environmentConfig;
    }


    public List<ArtifactResult> searchArtifacts(String environment,
                                                ArtifactSearchCriteria criteria) {
        return environmentConfig.getEnvironments().stream()
                .filter(env -> env.getName().equalsIgnoreCase(environment))
                .flatMap(env -> env.getApps().stream()
                        .map(app -> fetchArtifacts(app.getName()))
                        .flatMap(List::stream)
                        .filter(artifact -> criteriaMatch(artifact, criteria)))
                .collect(Collectors.toList());
    }

    private boolean criteriaMatch(ArtifactResult artifact, ArtifactSearchCriteria criteria) {
        return criteriaMatch(artifact.getVersionTimestamp(), criteria.getVersionTimestamp())
                && criteriaMatch(artifact.getBuildInformation().getGradleVersion(), criteria.getGradleVersion())
                && imageNameCriteriaMatch(artifact.getBuildInformation().getJenkinsImages(), criteria.getImageCriteria())
                && dependencyCriteriaMatch(artifact.getBuildInformation().getJavaDependencies(), criteria.getDependencyCriteria())
                && criteriaMatch(artifact.getBuildInformation().getJavaVersion(), criteria.getJavaVersionCriteria())
                && kubernetesObjectCriteriaMatch(artifact.getKubernetesObjects(), criteria.getKubernetesObjectCriteria());
    }

    private boolean equalCriteriaMatch(String fieldValue, String criteria) {
        if (criteria == null)
            return true;
        return criteria.equals(fieldValue);
    }

    private boolean criteriaMatch(String fieldValue, Criteria criteria) {
        if (criteria == null) {
            return true;
        }

        String criteriaValue = criteria.getValue();
        switch (criteria.getComparison()) {
            case EQUAL:
                return fieldValue.equals(criteriaValue);
            case NOT_EQUAL:
                return !fieldValue.equals(criteriaValue);
            case GREATER_THAN:
                return fieldValue.compareTo(criteriaValue) > 0;
            case LESS_THAN:
                return fieldValue.compareTo(criteriaValue) < 0;
            default:
                return false;
        }
    }

    private boolean imageNameCriteriaMatch(Map<String, ContainerImageInfo> images, ImageSearchCriteria criteria) {
        if (criteria == null) {
            return true;
        }

        for (ContainerImageInfo image : images.values()) {
            if (equalCriteriaMatch(image.getName(), criteria.getName())
                    && criteriaMatch(image.getVersion(), criteria.getVersion())
                    && criteriaMatch(image.getNamespace(), criteria.getNamespace())) {
                return true;
            }
        }
        return false;
    }

    private boolean dependencyCriteriaMatch(Map<String, JavaDependency> dependencies, DependencySearchCriteria criteria) {
        if (criteria == null) {
            return true;
        }

        for (JavaDependency dependency : dependencies.values()) {
            if (equalCriteriaMatch(dependency.getGroupId(), criteria.getGroupId())
                    && equalCriteriaMatch(dependency.getArtifactId(), criteria.getArtifactId())
                    && criteriaMatch(dependency.getVersion(), criteria.getVersion())) {
                return true;
            }
        }
        return false;
    }

    private boolean kubernetesObjectCriteriaMatch(List<KubernetesObject> kubernetesObjects, KubernetesObjectSearchCriteria criteria) {
        if (criteria == null) {
            return true;
        }

        for (KubernetesObject kubernetesObject : kubernetesObjects) {
            if (equalCriteriaMatch(kubernetesObject.getName(), criteria.getName())
                    && criteriaMatch(kubernetesObject.getVersion(), criteria.getVersion())) {
                return true;
            }
        }
        return false;
    }

    // Placeholder method to simulate fetching artifacts from an external service
    private List<ArtifactResult> fetchArtifacts(String appName) {


        return DummyDataGenerator.generateDummyData()
                .stream().filter(app -> app.getApplicationName().equals(appName))
                .collect(Collectors.toList());
    }
}

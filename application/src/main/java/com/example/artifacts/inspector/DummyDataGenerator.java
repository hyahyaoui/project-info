package com.example.artifacts.inspector;

import com.example.artifacts.inspector.commons.model.BuildInformation;
import com.example.artifacts.inspector.commons.model.ContainerImageInfo;
import com.example.artifacts.inspector.commons.model.JavaDependency;
import com.example.artifacts.inspector.commons.model.KubernetesObject;
import com.example.artifacts.inspector.model.search.result.ArtifactResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyDataGenerator {

    public static List<ArtifactResult> generateDummyData() {
        List<ArtifactResult> dummyArtifacts = new ArrayList<>();

        // Generate dummy data for three applications
        dummyArtifacts.add(generateDummyArtifact("App1", "UAT", "123", "1.0", "11.0"));
        dummyArtifacts.add(generateDummyArtifact("App2", "UAT", "124", "1.1", "12.0"));
        dummyArtifacts.add(generateDummyArtifact("App3", "UAT", "125", "1.2", "13.0"));

        return dummyArtifacts;
    }

    private static ArtifactResult generateDummyArtifact(String appName, String environment, String versionTimestamp, String gradleVersion, String javaVersion) {
        ArtifactResult artifact = new ArtifactResult();
        artifact.setApplicationName(appName);
        artifact.setEnvironment(environment);
        artifact.setVersionTimestamp(versionTimestamp);

        BuildInformation buildInformation = new BuildInformation();
        buildInformation.setGradleVersion(gradleVersion);
        buildInformation.setJavaVersion(javaVersion);

        // Generate dummy Jenkins images
        Map<String, ContainerImageInfo> jenkinsImages = new HashMap<>();
        jenkinsImages.put("jenkins-image-1", generateDummyContainerImage("nginx", "1.14.2", "default"));
        jenkinsImages.put("jenkins-image-2", generateDummyContainerImage("mysql", "5.7.28", "custom-namespace"));
        buildInformation.setJenkinsImages(jenkinsImages);

        // Generate dummy Java dependencies
        Map<String, JavaDependency> javaDependencies = new HashMap<>();
        javaDependencies.put("dependency-1", generateDummyJavaDependency("org.springframework.boot", "spring-boot-starter-web", "2.6.3"));
        javaDependencies.put("dependency-2", generateDummyJavaDependency("com.google.guava", "guava", "30.1-jre"));
        buildInformation.setJavaDependencies(javaDependencies);

        artifact.setBuildInformation(buildInformation);

        // Generate dummy Kubernetes objects
        List<KubernetesObject> kubernetesObjects = new ArrayList<>();
        kubernetesObjects.add(generateDummyKubernetesObject("deployment-1", "v1"));
        kubernetesObjects.add(generateDummyKubernetesObject("deployment-2", "v2"));
        artifact.setKubernetesObjects(kubernetesObjects);

        return artifact;
    }

    private static ContainerImageInfo generateDummyContainerImage(String name, String version, String namespace) {
        ContainerImageInfo image = new ContainerImageInfo();
        image.setName(name);
        image.setVersion(version);
        image.setNamespace(namespace);
        return image;
    }

    private static JavaDependency generateDummyJavaDependency(String groupId, String artifactId, String version) {
        JavaDependency dependency = new JavaDependency();
        dependency.setGroupId(groupId);
        dependency.setArtifactId(artifactId);
        dependency.setVersion(version);
        return dependency;
    }

    private static KubernetesObject generateDummyKubernetesObject(String name, String version) {
        KubernetesObject kubernetesObject = new KubernetesObject();
        kubernetesObject.setName(name);
        kubernetesObject.setVersion(version);
        return kubernetesObject;
    }
}

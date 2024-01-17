
# Project Artifacts Info Gradle Plugin

## Overview

The Project Artifacts Info Gradle Plugin is designed to gather essential information about a Gradle project and produce a JSON file containing relevant data. This includes details such as the Java version, Gradle version, project dependencies, and Kubernetes container images.

## Features

- **JavaInfoCollector:** Captures information regarding the Java version employed in the project.
- **GradleInfoCollector:** Retrieves details about the Gradle version used in the project.
- **DependenciesCollector:** Gathers comprehensive information about project dependencies.
- **KubernetesInfoCollector:** Extracts details about container images specified in the KubernetesPod.yaml file.

## Usage

### Apply the Plugin

Apply the plugin in your `build.gradle` file:

```groovy
plugins {
    id 'com.example.project-artifacts-info' version '1.0.0'
}
```

## Configuration

The plugin provides the following configurations in the `projectArtifactsInfo` extension:

- **artifacts:** Specify a list of artifacts to collect. Possible values include 'javaInfo', 'gradleInfo', 'dependenciesInfo', 'kubernetesInfo'.
  (**NB:** If you omit the artifacts configuration, the plugin will automatically collect information for all available artifacts.)
- **kubernetesPodFilePath:** Define the path to the KubernetesPod.yaml file. The default is 'path/to/KubernetesPod.yaml'.
- **outputJsonFilePath:** Set the output path for the generated JSON file. The default is 'META-INF/project-info.json'.

### Example:

```groovy
projectArtifactsInfo {
    artifacts = ['javaInfo', 'gradleInfo', 'dependenciesInfo', 'kubernetesInfo']
    kubernetesPodFilePath = 'path/to/KubernetesPod.yaml'
    outputJsonFilePath = 'META-INF/project-info.json'
}

package com.example.tasks;

import com.example.collectors.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.artifacts.ResolvableDependencies;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectArtifactsInfoTask extends DefaultTask {
    @Input
    List<String> artifactToScan;

    @Internal
    Map<String, ArtifactInfoCollectorFunction> collectors = new HashMap<>();

    @OutputFile
    File outputJsonFile = new File(getProject().getProjectDir(), "src/main/resources/project-info.json");

    @TaskAction
    void collectProjectInfo() {
        initCollectors();
        getLogger().quiet("Collecting Project Information...");

        Map<String, ArtifactInfo> collectedInfo = new HashMap<>();

        if (artifactToScan != null && !artifactToScan.isEmpty()) {
            collectors.keySet().retainAll(artifactToScan);
        }

        collectors.forEach((collectorName, collectorFunction) -> {
            getLogger().quiet("Running collector: " + collectorName);
            ArtifactInfo artifactInfo = collectorFunction.getInfo(getProject());
            getLogger().quiet("Executed collector: " + collectorName);
            collectedInfo.put(collectorName, artifactInfo);
        });

        // Write the collected info to the output JSON file
        writeCollectedInfoToFile(collectedInfo);
    }



    private void initCollectors() {
        collectors.put("javaInfo", new JavaInfoCollector());
        collectors.put("gradleInfo", new GradleInfoCollector());
        collectors.put("dependenciesInfo", new DependenciesCollector());
    }

    private void writeCollectedInfoToFile(Map<String, ArtifactInfo> collectedInfo) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(outputJsonFile, collectedInfo);
            getLogger().quiet("Collected info written to file: " + outputJsonFile.getAbsolutePath());
        } catch (IOException e) {
            getLogger().warn("Failed to write collected info to file", e);
        }
    }

    public File getOutputJsonFile() {
        return outputJsonFile;
    }
    public List<String> getArtifactToScan() {
        return artifactToScan;
    }
    public Map<String, ArtifactInfoCollectorFunction> getCollectors() {
        return collectors;
    }
    public void setArtifactToScan(List<String> artifactToScan) {
        this.artifactToScan = artifactToScan;
    }



}

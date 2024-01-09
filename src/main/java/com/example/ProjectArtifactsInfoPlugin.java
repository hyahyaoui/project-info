package com.example;

import com.example.extension.ProjectArtifactsInfoPluginExtension;
import com.example.tasks.ProjectArtifactsInfoTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.ResolvableDependencies;

public class ProjectArtifactsInfoPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        // Create an extension for the plugin
        ProjectArtifactsInfoPluginExtension extension = project.getExtensions().create("projectArtifactsInfo", ProjectArtifactsInfoPluginExtension.class);


        // Create the task and configure it with the extension parameters
        project.getTasks().create("collectProjectInfo", ProjectArtifactsInfoTask.class, task -> {
            task.setArtifactToScan(extension.getArtifacts());
        });

        // Configure the default task to depend on the custom task

        project.getTasks().getByName("processResources").dependsOn("collectProjectInfo");

    }
}

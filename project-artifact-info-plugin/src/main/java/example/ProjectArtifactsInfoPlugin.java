package example;

import example.tasks.ProjectArtifactsInfoTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class ProjectArtifactsInfoPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        // Create the task and configure it with the extension parameters
        project.getTasks().create("collectProjectInfo", ProjectArtifactsInfoTask.class);

        // Configure the default task to depend on the custom task

        project.getTasks().getByName("processResources").dependsOn("collectProjectInfo");
        project.getDependencies().add("implementation", "com.example:projectinfostarter:1.0.0-SNAPSHOT");

    }
}

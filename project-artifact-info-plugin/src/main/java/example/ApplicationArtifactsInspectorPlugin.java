package example;

import example.tasks.ApplicationArtifactsInspector;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class ApplicationArtifactsInspectorPlugin implements Plugin<Project> {

    @Override

    public void apply(Project project) {

        project.getTasks().create("applicationArtifactsInspector", ApplicationArtifactsInspector.class);
        project.getTasks().getByName("processResources").dependsOn("applicationArtifactsInspector");
        project.getDependencies().add("implementation", "com.example.app-artifacts-inspector:artifacts-inspector-starter:1.0.0-SNAPSHOT");


    }
}


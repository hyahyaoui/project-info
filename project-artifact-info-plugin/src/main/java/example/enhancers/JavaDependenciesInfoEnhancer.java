package example.enhancers;

import com.example.artifacts.inspector.commons.model.ApplicationArtifactsInfo;
import com.example.artifacts.inspector.commons.model.JavaDependency;
import org.gradle.api.Project;

public class JavaDependenciesInfoEnhancer implements ArtifactInfoEnhancer {
    private static final String RUNTIME_CLASSPATH_CONFIGURATION = "runtimeClasspath";
    private ArtifactInfoEnhancer nextEnhancer;

    @Override
    public void enhance(Project project, ApplicationArtifactsInfo applicationArtifactsInfo) {
        project.getConfigurations()
                .getByName(RUNTIME_CLASSPATH_CONFIGURATION)
                .getFiles().forEach(file -> {
            String[] parts = file.getAbsolutePath().split("\\\\");
            if (parts.length >= 5) {
                String artifactId = parts[parts.length - 4];
                String version = parts[parts.length - 3];
                JavaDependency javaDependency = new JavaDependency();
                //applicationArtifactsInfo.getBuildInformation().getJavaDependencies().put(javaDependency.);
            }
        });

        if (nextEnhancer != null) {
            nextEnhancer.enhance(project, applicationArtifactsInfo);
        }
    }

    @Override
    public ArtifactInfoEnhancer setNextEnhancer(ArtifactInfoEnhancer nextEnhancer) {
        this.nextEnhancer = nextEnhancer;
        return this;
    }

    @Override
    public ArtifactInfoEnhancer getNextNextEnhancer() {
        return nextEnhancer;
    }
}

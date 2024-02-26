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
                .getResolvedConfiguration()
                .getResolvedArtifacts().forEach(artifact -> {
                    final String gav = artifact.getId().getComponentIdentifier().getDisplayName();
                    final String[] gavArray = gav.split(":");
                    if (gavArray.length >= 5) {
                        String groupId = gavArray[0];
                        String artifactId = gavArray[1];
                        String version = gavArray[2];
                        JavaDependency javaDependency = new JavaDependency(groupId, artifactId, version);
                        applicationArtifactsInfo.getBuildInformation().getJavaDependencies().put(artifactId, javaDependency);
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

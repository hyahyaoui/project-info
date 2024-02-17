package example.enhancers;

import com.example.artifacts.inspector.commons.model.ApplicationArtifactsInfo;
import org.gradle.api.Project;

public class GradleInfoEnhancer implements ArtifactInfoEnhancer {
    private ArtifactInfoEnhancer nextEnhancer;

    @Override
    public void enhance(Project project, ApplicationArtifactsInfo applicationArtifactsInfo) {
        String gradleVersion = project.getGradle().getGradleVersion();
        applicationArtifactsInfo.getBuildInformation().setGradleVersion(gradleVersion);

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

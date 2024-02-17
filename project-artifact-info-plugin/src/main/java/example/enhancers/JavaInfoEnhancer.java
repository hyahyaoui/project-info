package example.enhancers;

import com.example.artifacts.inspector.commons.model.ApplicationArtifactsInfo;
import org.gradle.api.JavaVersion;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginExtension;

public class JavaInfoEnhancer implements ArtifactInfoEnhancer {
    private ArtifactInfoEnhancer nextEnhancer;

    @Override
    public void enhance(Project project, ApplicationArtifactsInfo applicationArtifactsInfo) {
        JavaPluginExtension javaPluginExtension = project.getExtensions().findByType(JavaPluginExtension.class);
        JavaVersion javaVersion = (javaPluginExtension != null) ? javaPluginExtension.getSourceCompatibility() : null;
        String version = (javaVersion != null) ? javaVersion.toString() : "Unknown";
        applicationArtifactsInfo.getBuildInformation().setJavaVersion(version);

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

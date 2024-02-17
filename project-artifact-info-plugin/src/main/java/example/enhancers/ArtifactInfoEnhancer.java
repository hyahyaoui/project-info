package example.enhancers;

import com.example.artifacts.inspector.commons.model.ApplicationArtifactsInfo;
import org.gradle.api.Project;

public interface ArtifactInfoEnhancer {
    void enhance(Project project, ApplicationArtifactsInfo applicationArtifactsInfo);
    ArtifactInfoEnhancer setNextEnhancer(ArtifactInfoEnhancer nextEnhancer);
    ArtifactInfoEnhancer getNextNextEnhancer();
}

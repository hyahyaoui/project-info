import com.example.artifacts.inspector.commons.model.ApplicationArtifactsInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

@AutoConfiguration
public class ArtifactsInformationEndpointAutoConfigure {

    private final String buildInfoFilePath = "/META-INF/build-info.json";

    private final ResourceLoader resourceLoader;

    public ArtifactsInformationEndpointAutoConfigure(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public ArtifactsInfoEndPoint projectInfoEndpoint() throws IOException {

        final Resource resource = this.resourceLoader.getResource(buildInfoFilePath);
        ApplicationArtifactsInfo applicationAntifactsInfo = new ApplicationArtifactsInfo();

        if (resource.exists()) {
            applicationAntifactsInfo = new ObjectMapper().readValue(resource.getContentAsByteArray(), ApplicationArtifactsInfo.class);
        }

        return new ArtifactsInfoEndPoint(applicationAntifactsInfo);
    }

    @Endpoint(id = "app-artifacts-info")
    public static class ArtifactsInfoEndPoint {

        private final ApplicationArtifactsInfo applicationArtifactsInfo;

        public ArtifactsInfoEndPoint(ApplicationArtifactsInfo applicationArtifactsInfo) {
            this.applicationArtifactsInfo = applicationArtifactsInfo;
        }

        @ReadOperation
        public ApplicationArtifactsInfo readProjectInfo() {
            return applicationArtifactsInfo;
        }
    }

}
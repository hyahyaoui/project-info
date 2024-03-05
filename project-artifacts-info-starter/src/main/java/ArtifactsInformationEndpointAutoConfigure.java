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



    private static void addK8sObjects (Path path, ApplicationArtifactsInfo applicationArtifactsInfo)

    BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream path tofile()); BufferedReader String line;
/*
    File may contain multiple khubernates objects

    Example:

    apiVersion: autoscaling/v2

    kind: Horizontal PodAutoscaler

    spec:

    scalaTargetRef

    apiVersion: apps/vi

    kind: Deployment

    and may also contain multiple version for Kubernetes Object depending on some condition

    ره

            Example

11-st semvarCompare spiVersion: networking #s.io/vl

1.15-0 Capabilities. FubeVersion, GitVersion -

            else if semvexCompare "1.14-0" Capabilities. KubeVersion. GitVersion -11

    spiVersion: networking, klu.so/vibetal

else -1)

    apiVersson extensions/Vibetal

1 and 11

    kind: Ingres

    We have to stop at the first object and its possivle versions
*/
    String stopAt ="";

    String kind ="";


            while ((Line Input readLine() in muli 44 iane, indexOf (stopAt)-1)(


    if(line.tolowerCase()).indexOf("kind")>-1) {

    stopkt stopât null ? "kind" stopAt kind line strip!).split("").

            (Line toloverCase() indexOf("apiVersion")-11

    stopke stopat mollapiVersion stopas Steing version lane stripil split("") (1)

    KubernatesObjecs kubernetesObject new RubeznatasObject())

            kubernetesObject.setVersion(version):

    applicationAstifartsInfo getubernetesObjects).add(ubernatesObject)

    String finalKind kind.

            applicationArtifactsInfo.getKubernetesbjects()

    SEEWAN) forEach (Buberneteshject( kubernetesObject setifame (finalKind),

    Input cl

    rasch (Exception

                   ex printsackTrace()
}
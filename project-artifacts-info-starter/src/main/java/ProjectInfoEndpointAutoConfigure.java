import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@AutoConfiguration
public class ProjectInfoEndpointAutoConfigure {


    public ProjectInfoEndpointAutoConfigure() {
        System.out.println("Hello");
    }

    @Bean
    @ConditionalOnProperty(name = "management.endpoints.web.exposure.include", havingValue = "project-info", matchIfMissing = true)
    public ProjectInfoEndpoint projectInfoEndpoint() {
        return new ProjectInfoEndpoint();
    }

    @Bean
    public InfoContributor projectInfoContributor() {
        // Implement your own InfoContributor logic here if needed
        return builder -> builder.withDetail("pinf", "Your project info details");
    }
    @Endpoint(id = "project-info")
    public static class ProjectInfoEndpoint {

        @ReadOperation
        public String readProjectInfo() {
            // Specify the path of the file relative to META-INF
            String filePath = "META-INF/project-info.json";

            // Get the class loader
            ClassLoader classLoader = this.getClass().getClassLoader();

            // Try to load the file as an input stream
            try (InputStream inputStream = classLoader.getResourceAsStream(filePath)) {
                System.out.println("reading" );
                System.out.println(inputStream);
                System.out.println("end is" );

                if (inputStream != null) {
                    System.out.println("inputStream not null" );

                    // Read the content of the file using a Scanner
                    try (Scanner scanner = new Scanner(inputStream)) {
                        scanner.useDelimiter("\\A"); // Use beginning of input as delimiter
                        return scanner.hasNext() ? scanner.next() : "";
                    }
                }
            } catch (IOException e) {
                System.out.println("exception" );
                System.out.println(e);
                e.printStackTrace();
            }

            // Return an empty string if the file doesn't exist or there was an error
            return "{}";
        }
    }
}

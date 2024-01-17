package example.collectors;

import org.gradle.api.JavaVersion;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginExtension;

public class JavaInfoCollector implements ArtifactInfoCollectorFunction {
    @Override
    public JavaInfo getInfo(Project project) {
        JavaPluginExtension javaPluginExtension = project.getExtensions().findByType(JavaPluginExtension.class);
        JavaVersion javaVersion = javaPluginExtension != null ? javaPluginExtension.getSourceCompatibility() : null;
        String version = javaVersion != null ? javaVersion.toString() : "Unknown";
        return new JavaInfo(version);
    }

    public static class JavaInfo implements ArtifactInfo {
        private String version;

        public JavaInfo(String version) {
            this.version = version;
        }

        public String getVersion() {
            return version;
        }
    }
}

package example.collectors;

import io.kubernetes.client.openapi.models.V1Service
import io.kubernetes.client.util.Yaml
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

class KubernetesObjectsPlugin implements Plugin<Project> {

    @Option(option = "inputFolder", description = "Folder containing YAML files with Kubernetes objects")
    @Input
    String inputFolder

    @Option(option = "outputFile", description = "Output file to store parsed Kubernetes objects")
    @Input
    String outputFile

    @Override
    void apply(Project project) {
        // Register a task for parsing Kubernetes objects
        project.tasks.register("parseKubernetesObjects", ParseKubernetesObjectsTask)
    }

    class ParseKubernetesObjectsTask extends DefaultTask {
        @TaskAction
        void parseYamlFiles() {
            if (inputFolder == null || outputFile == null) {
                throw new GradleException("Both 'inputFolder' and 'outputFile' options are required.")
            }

            File folder = project.file(inputFolder)
            File[] yamlFiles = folder.listFiles { dir, name -> name.endsWith(".yaml") || name.endsWith(".yml") }

            if (yamlFiles) {
                List<String> kubernetesObjects = []

                yamlFiles.each { file ->
                    String yamlContent = file.text
                    def k8sObject = Yaml.loadAs(yamlContent, Map)

                    if (k8sObject) {
                        String kind = k8sObject['kind']
                        String apiVersion = k8sObject['apiVersion']

                        if (kind && apiVersion) {
                            kubernetesObjects.add("$kind $apiVersion")
                        }
                    }
                }

                File output = project.file(outputFile)
                output.text = kubernetesObjects.join('\n')

                logger.lifecycle("Kubernetes objects types and versions extracted and saved to: ${outputFile}")
            } else {
                logger.lifecycle("No YAML files found in the specified folder: ${inputFolder}")
            }
        }
    }
}

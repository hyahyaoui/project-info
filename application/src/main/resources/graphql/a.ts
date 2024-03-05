private static void addK8sObjects(Path path, ApplicationArtifactsInfo applicationArtifactsInfo) {
    try (BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile())))) {
        String line;
        String stopAt = null;
        String kind = null;

        while ((line = input.readLine()) != null && (stopAt == null || line.indexOf(stopAt) == -1)) {
            if (line.toLowerCase().contains("kind")) {
                stopAt = (stopAt == null) ? "kind" : stopAt;
                kind = line.strip().split(":")[1].trim();
            }

            if (line.toLowerCase().contains("apiversion")) {
                stopAt = (stopAt == null) ? "apiversion" : stopAt;
                String version = line.strip().split(":")[1].trim();

                KubernetesObject kubernetesObject = new KubernetesObject();
                kubernetesObject.setVersion(version);
                applicationArtifactsInfo.getKubernetesObjects().add(kubernetesObject);
            }
        }

        applicationArtifactsInfo.getKubernetesObjects().forEach(kubernetesObject -> kubernetesObject.setName(kind));
    } catch (IOException e) {
        e.printStackTrace();
    }
}

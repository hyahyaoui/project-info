package example.enhancers;

import com.example.artifacts.inspector.commons.model.ApplicationArtifactsInfo;
import com.example.artifacts.inspector.commons.model.ContainerImageInfo;
import org.gradle.api.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class JenkinsBuildImagesInfoEnhancerTest {

    private JenkinsBuildImagesInfoEnhancer enhancer;

    @Mock
    private Project project;

    @Mock
    private ApplicationArtifactsInfo applicationArtifactsInfo;

    @Mock
    private FileInputStream fis;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        enhancer = new JenkinsBuildImagesInfoEnhancer("kubernetes-pod.yaml");
    }

    @Test
    void testEnhanceWithValidKubernetesPodFile() throws IOException {
        final File kubernetesPodFile = mock(File.class);
        final File projectDir = mock(File.class);
        final FileInputStream fis = mock(FileInputStream.class);
        when(project.getProjectDir()).thenReturn(projectDir);
        when(kubernetesPodFile.getCanonicalFile()).thenReturn(kubernetesPodFile);
        when(kubernetesPodFile.exists()).thenReturn(true);
        when(FileInputStream.class).

        enhancer.enhance(project,applicationArtifactsInfo);
        // Verify that the FileInputStream is closed
        verify(fis, times(1)).close();
    }

    // Other tests...

    private Map<String, Object> createMockYamlData() {
        // Create and return a Map<String, Object> with mock YAML data
        // Adjust the content based on your YAML structure
        return Collections.emptyMap();
    }
}

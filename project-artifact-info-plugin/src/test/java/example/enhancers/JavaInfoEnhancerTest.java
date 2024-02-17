package example.enhancers;

import com.example.artifacts.inspector.commons.model.ApplicationArtifactsInfo;
import com.example.artifacts.inspector.commons.model.BuildInformation;
import org.gradle.api.JavaVersion;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.plugins.JavaPluginExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class JavaInfoEnhancerTest {

    @Mock
    private Project project;

    @Mock
    private ApplicationArtifactsInfo applicationArtifactsInfo;

    @Mock
    private JavaPluginExtension javaPluginExtension;

    @Mock
    private BuildInformation buildInformation;

    @Mock
    private ArtifactInfoEnhancer nextEnhancer;

    @Mock
    ExtensionContainer extensionContainer;

    @InjectMocks
    private JavaInfoEnhancer enhancer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(applicationArtifactsInfo.getBuildInformation()).thenReturn(buildInformation);
        when(project.getExtensions()).thenReturn(extensionContainer);

    }

    @Test
    void testEnhanceWithJavaPluginExtension() {
        // Arrange
        when(project.getExtensions().findByType(JavaPluginExtension.class)).thenReturn(javaPluginExtension);
        when(javaPluginExtension.getSourceCompatibility()).thenReturn(JavaVersion.VERSION_1_8);

        // Act
        enhancer.enhance(project, applicationArtifactsInfo);

        // Assert
        verify(buildInformation).setJavaVersion("1.8");
    }

    @Test
    void testEnhanceWithoutJavaPluginExtension() {
        // Arrange
        when(project.getExtensions().findByType(JavaPluginExtension.class)).thenReturn(null);

        // Act
        enhancer.enhance(project, applicationArtifactsInfo);

        // Assert
        verify(buildInformation).setJavaVersion("Unknown");
    }

    @Test
    void testSetNextEnhancer() {
        // Arrange
        ArtifactInfoEnhancer nextEnhancer = mock(ArtifactInfoEnhancer.class);

        // Act
        enhancer.setNextEnhancer(nextEnhancer);

        // Assert
        assertEquals(nextEnhancer, enhancer.getNextNextEnhancer());
    }

}

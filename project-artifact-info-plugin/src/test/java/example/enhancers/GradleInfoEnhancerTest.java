package example.enhancers;

import com.example.artifacts.inspector.commons.model.ApplicationArtifactsInfo;
import com.example.artifacts.inspector.commons.model.BuildInformation;
import org.gradle.api.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GradleInfoEnhancerTest {

    @Mock
    private Project project;
    @Mock
    private ApplicationArtifactsInfo applicationArtifactsInfo;
    @Mock
    private BuildInformation buildInformation;
    @Mock
    private org.gradle.api.invocation.Gradle gradle; // Mock the Gradle class

    private GradleInfoEnhancer enhancer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(applicationArtifactsInfo.getBuildInformation()).thenReturn(buildInformation);
        enhancer = new GradleInfoEnhancer();

        // Mock Gradle and its methods
        when(project.getGradle()).thenReturn(gradle);
    }

    @Test
    void testEnhance() {
        // Arrange
        String gradleVersion = "7.0.1";
        when(gradle.getGradleVersion()).thenReturn(gradleVersion);

        // Act
        enhancer.enhance(project, applicationArtifactsInfo);

        // Assert
        verify(buildInformation).setGradleVersion(gradleVersion);
        verifyNoMoreInteractions(buildInformation);
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

import com.example.artifacts.inspector.commons.model.ApplicationArtifactsInfo;
import com.example.artifacts.inspector.commons.model.JavaDependency;
import example.enhancers.JavaDependenciesInfoEnhancer;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ResolvedArtifact;
import org.gradle.api.artifacts.component.ComponentArtifactIdentifier;
import org.gradle.internal.component.external.model.DefaultModuleComponentArtifactIdentifier;
import org.gradle.internal.component.external.model.DefaultModuleComponentIdentifier;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class JavaDependenciesInfoEnhancerTest {

    private JavaDependenciesInfoEnhancer enhancer;
    private Project project;
    private ApplicationArtifactsInfo applicationArtifactsInfo;

    @Mock
    private ResolvedArtifact resolvedArtifactMock;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        enhancer = new JavaDependenciesInfoEnhancer();
        project = ProjectBuilder.builder().build();
        applicationArtifactsInfo = new ApplicationArtifactsInfo();
    }

    @Test
    void enhanceAddsJavaDependencyToApplicationArtifactsInfo() {
        // Arrange
        Configuration configuration = project.getConfigurations().create("runtimeClasspath");

        DefaultModuleComponentArtifactIdentifier componentArtifactIdentifier =
                new DefaultModuleComponentArtifactIdentifier(null, "name", "jar", "jar");

        when(resolvedArtifactMock.getId()).thenReturn(componentArtifactIdentifier);


        // Act
        enhancer.enhance(project, applicationArtifactsInfo);

        // Assert
        Set<JavaDependency> javaDependencies = (Set<JavaDependency>) applicationArtifactsInfo.getBuildInformation().getJavaDependencies().values();
        assertEquals(1, javaDependencies.size());

        JavaDependency javaDependency = javaDependencies.iterator().next();
        assertEquals("group", javaDependency.getGroupId());
        assertEquals("sample", javaDependency.getArtifactId());
        assertEquals("1.0", javaDependency.getVersion());

        // Verify that the methods of resolvedArtifactMock were called
        verify(resolvedArtifactMock, times(2)).getId();
        verifyNoMoreInteractions(resolvedArtifactMock);
    }
}

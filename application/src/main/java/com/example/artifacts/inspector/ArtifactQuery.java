package com.example.artifacts.inspector;

import com.example.artifacts.inspector.model.search.input.ArtifactSearchCriteria;
import com.example.artifacts.inspector.model.search.result.ArtifactResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ArtifactQuery {

    private final ArtifactSearchService artifactSearchService;

    @Autowired
    public ArtifactQuery(ArtifactSearchService artifactSearchService) {
        this.artifactSearchService = artifactSearchService;
    }

    @QueryMapping
    public List<ArtifactResult> searchArtifacts(@Argument(name = "environment") String environment,
                                                @Argument(name = "criteria" ) ArtifactSearchCriteria criteria) {
        return artifactSearchService.searchArtifacts(environment, criteria);
    }

}
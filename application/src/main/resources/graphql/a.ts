// artifact-search.component.ts

import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
    selector: 'app-artifact-search',
    templateUrl: './artifact-search.component.html',
    styleUrls: ['./artifact-search.component.css']
})
export class ArtifactSearchComponent implements OnInit {

    result: any; // You can define a specific type for the result based on your GraphQL schema

    constructor(private http: HttpClient) { }

    ngOnInit(): void {
        this.executeGraphQLQuery();
    }

    executeGraphQLQuery(): void {
        const query = `
      query SearchArtifacts($environment: String!, $criteria: ArtifactSearchCriteria!) {
        searchArtifacts(environment: $environment, criteria: $criteria) {
          applicationName
          environment
          buildInformation {
            gradleVersion
            jenkinsImages {
              name
              version
              namespace
            }
            javaDependencies {
              groupId
              artifactId
              version
            }
            javaVersion
          }
          kubernetesObjects {
            name
            version
          }
        }
      }
    `;

        const variables = {
            environment: 'YourEnvironment', // Set your environment
            criteria: {
                gradleVersionCriteriaList: [{ value: 'YourGradleVersion', comparison: ComparisonType.EQUAL }],
                // ... Other criteria here
            }
        };

        const url = 'YourGraphQLServerURL'; // Set your GraphQL server URL

        this.http.post(url, { query, variables })
            .subscribe(response => {
                this.result = response['data'];
                console.log(this.result);
            });
    }
}

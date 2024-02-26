package graphql;// artifact-search.component.ts
import { Component } from '@angular/core';
import { Apollo } from 'apollo-angular';
import {
  Query,
  ArtifactSearchCriteria,
  Criteria,
  ImageSearchCriteria,
  DependencySearchCriteria,
  KubernetesObjectSearchCriteria,
  ComparisonType,
} from './types';

@Component({
  selector: 'app-artifact-search',
  templateUrl: './artifact-search.component.html',
  styleUrls: ['./artifact-search.component.scss'],
})
export class ArtifactSearchComponent {
  searchCriteria: ArtifactSearchCriteria = {
    gradleVersionCriteriaList: [new Criteria()],
    imageCriteriaList: [new ImageSearchCriteria()],
    dependencyCriteriaList: [new DependencySearchCriteria()],
    javaVersionCriteriaList: [new Criteria()],
    kubernetesObjectCriteriaList: [new KubernetesObjectSearchCriteria()],
  };

  comparisonTypes = Object.values(ComparisonType); // To populate the select box

  constructor(private apollo: Apollo) {}

  addCriteria(criteriaList: any[]) {
    criteriaList.push(this.createEmptyCriteria(criteriaList[0]));
  }

  removeCriteria(index: number, criteriaList: any[]) {
    if (criteriaList.length > 1) {
      criteriaList.splice(index, 1);
    }
  }

  createEmptyCriteria(template: any): any {
    return JSON.parse(JSON.stringify(template)); // Deep copy the template
  }

  searchArtifacts() {
    const criteria: ArtifactSearchCriteria = { ...this.searchCriteria };

    this.apollo
      .watchQuery<Query>({
        query: YOUR_GRAPHQL_QUERY,
        variables: {
          criteria: criteria,
          environment: 'your_environment',
        },
      })
      .valueChanges.subscribe(({ data }) => {
        // Handle the response as needed
      });
  }
}

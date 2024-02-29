import { Component } from '@angular/core';
import { Apollo } from 'apollo-angular';
import {
ArtifactSearchCriteria,
CiImageSearchCriteria,
Criteria,
JavaDependencySearchCriteria,
KubernetesObjectSearchCriteria,
} from 'path-to-your-models';

@Component({
selector: 'app-artifact-search',
templateUrl: './artifacts-search.component.html',
styleUrls: ['./artifacts-search.component.scss'],
})
export class ArtifactsSearchComponent {
query = '';
environment = 'UAT';

searchCriteria: ArtifactSearchCriteria = {
gradleVersionCriteriaList: [new Criteria()],
ciImageCriteriaList: [new CiImageSearchCriteria()],
javaDependenciesCriteriaList: [new JavaDependencySearchCriteria()],
javaVersionCriteriaList: [new Criteria()],
kubernetesObjectCriteriaList: [new KubernetesObjectSearchCriteria()],
};

comparisonTypes = Object.values(ComparisonType);

constructor(private apollo: Apollo) {}

addCriteria(criteriaList: any[]) {
criteriaList.push(this.createEmptyCriteria(criteriaList[0]));
}

removeCriteria(index: number, criteriaList: any[]) {
criteriaList.splice(index, 1);
}

createEmptyCriteria(template: any): any {
return {};
}

show() {
this.cleanCriteria();
}

cleanCriteria() {
const builtCriteria = { ...this.searchCriteria };

// Clean criteria for each field
builtCriteria.gradleVersionCriteriaList = this.cleanCriteriaList(builtCriteria.gradleVersionCriteriaList);
builtCriteria.ciImageCriteriaList = this.cleanCiImageCriteria(builtCriteria.ciImageCriteriaList);
builtCriteria.javaDependenciesCriteriaList = this.cleanJavaDependenciesCriteria(builtCriteria.javaDependenciesCriteriaList);
builtCriteria.kubernetesObjectCriteriaList = this.cleanKubernetesObjectCriteriaList(
builtCriteria.kubernetesObjectCriteriaList
);
builtCriteria.javaVersionCriteriaList = this.cleanCriteriaList(builtCriteria.javaVersionCriteriaList);

// Remove empty criteria fields
Object.keys(builtCriteria).forEach((key) => {
if (!builtCriteria[key] || builtCriteria[key].length === 0) {
delete builtCriteria[key];
}
});

// Construct GraphQL query
this.query = this.constructGraphQLQuery(builtCriteria);
console.log(this.query);

// Execute GraphQL query
this.apollo
.query({
query: // Your GraphQL query here,
})
.subscribe((result) => {
// Handle the result as needed
console.log(result);
});
}

constructGraphQLQuery(criteria: any): string {
const queryFields = [
'applicationName',
'environment',
...this.includeBuildInformation(criteria.gradleVersionCriteriaList, criteria.javaVersionCriteriaList),
...this.includeJenkinsImages(criteria.ciImageCriteriaList),
...this.includeJavaDependencies(criteria.javaDependenciesCriteriaList),
...this.includeKubernetesObjects(criteria.kubernetesObjectCriteriaList),
].filter(Boolean);

return `searchArtifacts(environment: "${this.environment}", criteria: ${JSON.stringify(criteria)}) { ${queryFields.join(' ')} }`;
}

includeBuildInformation(gradleVersionCriteriaList: any[], javaVersionCriteriaList: any[]): string[] {
const buildInformationFields = ['gradleVersion', 'javaVersion'];
const includedFields: string[] = [];

if (gradleVersionCriteriaList.length > 0) {
includedFields.push('buildInformation { ' + buildInformationFields.join(' ') + ' }');
}

if (javaVersionCriteriaList.length > 0) {
includedFields.push('buildInformation { ' + buildInformationFields.join(' ') + ' }');
}

return includedFields;
}

includeJenkinsImages(ciImageCriteriaList: any[]): string[] {
return ciImageCriteriaList.length > 0 ? ['buildInformation { jenkinsImages { name version namespace } }'] : [];
}

includeJavaDependencies(javaDependenciesCriteriaList: any[]): string[] {
return javaDependenciesCriteriaList.length > 0 ? ['buildInformation { javaDependencies { groupId artifactId version } }'] : [];
}

includeKubernetesObjects(kubernetesObjectCriteriaList: any[]): string[] {
return kubernetesObjectCriteriaList.length > 0 ? ['kubernetesObjects { name version }'] : [];
}

cleanCriteriaList(criteriaList: any[]): any[] {
return criteriaList.filter((criteria) => !!criteria.value);
}

cleanCiImageCriteria(ciImageCriteriaList: CiImageSearchCriteria[]): CiImageSearchCriteria[] {
return ciImageCriteriaList.filter(
(ciImageSearchCriteria) =>
!!ciImageSearchCriteria.name || !!ciImageSearchCriteria.namespace.value || !!ciImageSearchCriteria.version.value
);
}

cleanJavaDependenciesCriteria(
javaDependenciesCriteriaList: JavaDependencySearchCriteria[]
): JavaDependencySearchCriteria[] {
return javaDependenciesCriteriaList.filter(
(javaDependencySearchCriteria) =>
!!javaDependencySearchCriteria.groupId || !!javaDependencySearchCriteria.artifactId
);
}

cleanKubernetesObjectCriteriaList(
kubernetesObjectCriteriaList: KubernetesObjectSearchCriteria[]
): KubernetesObjectSearchCriteria[] {
return kubernetesObjectCriteriaList.filter(
(kubernetesObjectSearchCriteria) =>
!!kubernetesObjectSearchCriteria.name || !!kubernetesObjectSearchCriteria.version.value
);
}
}

enum ComparisonType {
EQUAL = 'EQUAL',
NOT_EQUAL = 'NOT_EQUAL',
GREATER_THAN = 'GREATER_THAN',
GREATER_THAN_OR_EQUAL = 'GREATER_THAN_OR_EQUAL',
LESS_THAN = 'LESS_THAN',
LESS_THAN_OR_EQUAL = 'LESS_THAN_OR_EQUAL',
}

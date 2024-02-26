package graphql;// artifact-search-criteria.model.ts
export class Criteria {
  value: string = '';
  comparison: ComparisonType = ComparisonType.EQUAL;
}

export class ImageSearchCriteria {
  name: string = '';
  version: Criteria = new Criteria();
  namespace: Criteria = new Criteria();
}

export class DependencySearchCriteria {
  groupId: string = '';
  artifactId: string = '';
  version: Criteria = new Criteria();
}

export class KubernetesObjectSearchCriteria {
  name: string = '';
  version: Criteria = new Criteria();
}

export class ArtifactSearchCriteria {
  gradleVersionCriteriaList: Criteria[] = [new Criteria()];
  imageCriteriaList: ImageSearchCriteria[] = [new ImageSearchCriteria()];
  dependencyCriteriaList: DependencySearchCriteria[] = [new DependencySearchCriteria()];
  javaVersionCriteriaList: Criteria[] = [new Criteria()];
  kubernetesObjectCriteriaList: KubernetesObjectSearchCriteria[] = [new KubernetesObjectSearchCriteria()];
}

export enum ComparisonType {
  EQUAL = 'EQUAL',
  NOT_EQUAL = 'NOT_EQUAL',
  GREATER_THAN = 'GREATER_THAN',
  GREATER_THAN_OR_EQUAL = 'GREATER_THAN_OR_EQUAL',
  LESS_THAN = 'LESS_THAN',
  LESS_THAN_OR_EQUAL = 'LESS_THAN_OR_EQUAL',
}

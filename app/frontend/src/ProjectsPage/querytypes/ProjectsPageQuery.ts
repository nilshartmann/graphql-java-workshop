/* tslint:disable */
/* eslint-disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL query operation: ProjectsPageQuery
// ====================================================

export interface ProjectsPageQuery_projects_owner {
  __typename: "User";
  /**
   * The human readable name of the person
   */
  name: string;
}

export interface ProjectsPageQuery_projects_category {
  __typename: "Category";
  name: string;
}

export interface ProjectsPageQuery_projects {
  __typename: "Project";
  id: string;
  /**
   * A a simple, concise title for your project
   */
  title: string;
  /**
   * The project owner
   */
  owner: ProjectsPageQuery_projects_owner;
  category: ProjectsPageQuery_projects_category;
}

export interface ProjectsPageQuery {
  /**
   * Return an unordered list of all projects
   */
  projects: ProjectsPageQuery_projects[];
}

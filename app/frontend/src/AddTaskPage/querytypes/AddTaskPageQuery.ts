/* tslint:disable */
/* eslint-disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL query operation: AddTaskPageQuery
// ====================================================

export interface AddTaskPageQuery_project {
  __typename: "Project";
  id: string;
  /**
   * A a simple, concise title for your project
   */
  title: string;
}

export interface AddTaskPageQuery_users {
  __typename: "User";
  /**
   * The human readable name of the person
   */
  name: string;
  id: string;
}

export interface AddTaskPageQuery {
  project: AddTaskPageQuery_project | null;
  users: AddTaskPageQuery_users[];
}

export interface AddTaskPageQueryVariables {
  projectId: string;
}

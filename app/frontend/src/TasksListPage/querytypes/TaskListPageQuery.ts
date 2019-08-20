/* tslint:disable */
/* eslint-disable */
// This file was automatically generated and should not be edited.

import { TaskState } from "./../../global-query-types";

// ====================================================
// GraphQL query operation: TaskListPageQuery
// ====================================================

export interface TaskListPageQuery_project_tasks_assignee {
  __typename: "User";
  /**
   * The human readable name of the person
   */
  name: string;
}

export interface TaskListPageQuery_project_tasks {
  __typename: "Task";
  id: string;
  /**
   * A concicse title of this task, that describes what to do
   */
  title: string;
  /**
   * Who works on this Task or should work on the task
   */
  assignee: TaskListPageQuery_project_tasks_assignee;
  state: TaskState;
}

export interface TaskListPageQuery_project {
  __typename: "Project";
  /**
   * A a simple, concise title for your project
   */
  title: string;
  id: string;
  /**
   * You split your Project into several tasks that you
   * # have to work on to finish this Project's goal
   */
  tasks: TaskListPageQuery_project_tasks[];
}

export interface TaskListPageQuery {
  project: TaskListPageQuery_project | null;
}

export interface TaskListPageQueryVariables {
  projectId: string;
}

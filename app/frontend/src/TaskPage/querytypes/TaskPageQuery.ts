/* tslint:disable */
/* eslint-disable */
// This file was automatically generated and should not be edited.

import { TaskState } from "./../../global-query-types";

// ====================================================
// GraphQL query operation: TaskPageQuery
// ====================================================

export interface TaskPageQuery_project_task_assignee {
  __typename: "User";
  /**
   * The human readable name of the person
   */
  name: string;
}

export interface TaskPageQuery_project_task {
  __typename: "Task";
  id: string;
  /**
   * A concicse title of this task, that describes what to do
   */
  title: string;
  /**
   * A complete and detailed description what should be done in this task.
   * The description should be understandable also by people that are not
   * familiar with this task, so they can get into without having
   * to ask for further details
   */
  description: string;
  /**
   * Who works on this Task or should work on the task
   */
  assignee: TaskPageQuery_project_task_assignee;
  /**
   * Deadline for this task
   */
  toBeFinishedAt: string;
  state: TaskState;
}

export interface TaskPageQuery_project {
  __typename: "Project";
  id: string;
  /**
   * A a simple, concise title for your project
   */
  title: string;
  /**
   * Get a task by it's unique id. Return null if that
   * task could not be found
   */
  task: TaskPageQuery_project_task | null;
}

export interface TaskPageQuery {
  project: TaskPageQuery_project | null;
}

export interface TaskPageQueryVariables {
  projectId: string;
  taskId: string;
}

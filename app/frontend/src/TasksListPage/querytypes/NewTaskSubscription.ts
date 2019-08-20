/* tslint:disable */
/* eslint-disable */
// This file was automatically generated and should not be edited.

import { TaskState } from "./../../global-query-types";

// ====================================================
// GraphQL subscription operation: NewTaskSubscription
// ====================================================

export interface NewTaskSubscription_newTask_assignee {
  __typename: "User";
  /**
   * The human readable name of the person
   */
  name: string;
}

export interface NewTaskSubscription_newTask {
  __typename: "Task";
  id: string;
  /**
   * A concicse title of this task, that describes what to do
   */
  title: string;
  /**
   * Who works on this Task or should work on the task
   */
  assignee: NewTaskSubscription_newTask_assignee;
  state: TaskState;
}

export interface NewTaskSubscription {
  newTask: NewTaskSubscription_newTask;
}

export interface NewTaskSubscriptionVariables {
  projectId: string;
}

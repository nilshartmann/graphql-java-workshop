/* tslint:disable */
/* eslint-disable */
// This file was automatically generated and should not be edited.

import { TaskState } from "./../../global-query-types";

// ====================================================
// GraphQL subscription operation: TaskChangeSubscription
// ====================================================

export interface TaskChangeSubscription_onTaskChange {
  __typename: "Task";
  id: string;
  state: TaskState;
}

export interface TaskChangeSubscription {
  onTaskChange: TaskChangeSubscription_onTaskChange;
}

export interface TaskChangeSubscriptionVariables {
  projectId: string;
}

/* tslint:disable */
/* eslint-disable */
// This file was automatically generated and should not be edited.

import { TaskState } from "./../../global-query-types";

// ====================================================
// GraphQL mutation operation: UpdateTaskStateMutation
// ====================================================

export interface UpdateTaskStateMutation_updateTaskState {
  __typename: "Task";
  id: string;
  state: TaskState;
}

export interface UpdateTaskStateMutation {
  /**
   * Change the state of the specified task
   */
  updateTaskState: UpdateTaskStateMutation_updateTaskState;
}

export interface UpdateTaskStateMutationVariables {
  taskId: string;
  newState: TaskState;
}

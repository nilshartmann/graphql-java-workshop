/* tslint:disable */
/* eslint-disable */
// This file was automatically generated and should not be edited.

import { TaskState } from "./../../global-query-types";

// ====================================================
// GraphQL fragment: TaskFragment
// ====================================================

export interface TaskFragment_assignee {
  __typename: "User";
  /**
   * The human readable name of the person
   */
  name: string;
}

export interface TaskFragment {
  __typename: "Task";
  id: string;
  /**
   * A concicse title of this task, that describes what to do
   */
  title: string;
  /**
   * Who works on this Task or should work on the task
   */
  assignee: TaskFragment_assignee;
  state: TaskState;
}

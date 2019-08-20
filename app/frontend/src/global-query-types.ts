/* tslint:disable */
/* eslint-disable */
// This file was automatically generated and should not be edited.

//==============================================================
// START Enums and Input Objects
//==============================================================

export enum TaskState {
  FINISHED = "FINISHED",
  NEW = "NEW",
  RUNNING = "RUNNING",
}

export interface AddTaskInput {
  title: string;
  description: string;
  toBeFinishedAt?: string | null;
  assigneeId: string;
}

//==============================================================
// END Enums and Input Objects
//==============================================================

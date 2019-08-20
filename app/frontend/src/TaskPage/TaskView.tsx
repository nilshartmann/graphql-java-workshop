import { TaskPageQuery_project_task } from "./querytypes/TaskPageQuery";
import Cardboard from "../components/Cardboard";
import styles from "./TaskPage.module.scss";
import Card, { InfoCard } from "../components/Card";
import { mapTaskState } from "../util/mapper";
import * as React from "react";
import moment from "moment";
import { TaskState } from "global-query-types";

interface TaskViewProps {
  task: TaskPageQuery_project_task;

  onTaskStateChange(task: TaskPageQuery_project_task, newState: TaskState): void;
}

export default function TaskView({ task, onTaskStateChange }: TaskViewProps) {
  const finishedUntil = moment(task.toBeFinishedAt);
  const finishedUntilString = finishedUntil.format("MMM D, YYYY");

  const actions = [
    {
      label: "Start",
      onExecute: () => onTaskStateChange(task, TaskState.RUNNING)
    },
    {
      label: "Stop",
      onExecute: () => onTaskStateChange(task, TaskState.FINISHED)
    }
  ];

  return (
    <>
      <Cardboard className={styles.TaskStateCardboard}>
        <InfoCard label="To be finished until" title={finishedUntilString} />
        <InfoCard label={"Assignee"} title={task.assignee.name} />
        <InfoCard label="State" title={mapTaskState(task.state)} actions={actions} />
      </Cardboard>

      <h1>Description</h1>
      <Card>{task.description}</Card>
    </>
  );
}

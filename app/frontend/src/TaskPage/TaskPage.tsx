import * as React from "react";
import styles from "./TaskPage.module.scss";
import { RouteComponentProps } from "react-router";
import gql from "graphql-tag";
import { TaskPageQuery, TaskPageQueryVariables, TaskPageQuery_project_task } from "./querytypes/TaskPageQuery";
import TaskView from "./TaskView";
import TaskPageHeader from "./TaskPageHeader";
import { useQuery, useMutation } from "@apollo/react-hooks";
import { TaskState } from "global-query-types";
import { UpdateTaskStateMutation, UpdateTaskStateMutationVariables } from "./querytypes/UpdateTaskStateMutation";

const TASK_QUERY = gql`
  query TaskPageQuery($projectId: ID!, $taskId: ID!) {
    project(id: $projectId) {
      id
      title
      task(id: $taskId) {
        id
        title
        description
        assignee {
          name
        }
        toBeFinishedAt
        state
      }
    }
  }
`;

const UPDATE_TASK_STATE_MUTATION = gql`
  mutation UpdateTaskStateMutation($taskId: ID!, $newState: TaskState!) {
    updateTaskState(taskId: $taskId, newState: $newState) {
      id
      state
    }
  }
`;

type TaskPageProps = RouteComponentProps<{ projectId: string; taskId: string }>;

export default function TaskPage(props: TaskPageProps) {
  const projectId = props.match.params.projectId;
  const taskId = props.match.params.taskId;

  const { loading, error, data } = useQuery<TaskPageQuery, TaskPageQueryVariables>(TASK_QUERY, {
    variables: { projectId, taskId }
  });

  const [runChangeTaskState] = useMutation<UpdateTaskStateMutation, UpdateTaskStateMutationVariables>(UPDATE_TASK_STATE_MUTATION);

  function changeTaskState(task: TaskPageQuery_project_task, newState: TaskState) {
    runChangeTaskState({
      variables: {
        taskId: task.id,
        newState
      }
    });
  }

  if (loading) {
    return <h2>Loading...</h2>;
  }
  if (error || !data) {
    return <h2>Sorry... Something failed while loading data </h2>;
  }

  if (!data.project) {
    return <h2>Project not found!</h2>;
  }

  return (
    <div className={styles.TaskPage}>
      <TaskPageHeader project={data.project} />
      {data.project.task ? (
        <TaskView task={data.project.task} onTaskStateChange={changeTaskState} />
      ) : (
        <h2>Task cannot be found</h2>
      )}
    </div>
  );
}

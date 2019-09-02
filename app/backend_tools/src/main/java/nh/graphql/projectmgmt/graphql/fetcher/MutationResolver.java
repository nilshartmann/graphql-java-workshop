package nh.graphql.projectmgmt.graphql.fetcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import nh.graphql.projectmgmt.domain.Task;
import nh.graphql.projectmgmt.domain.TaskService;
import nh.graphql.projectmgmt.domain.TaskState;

@Component
public class MutationResolver implements GraphQLMutationResolver {

  @Autowired
  private TaskService taskService;

  public Task addTask(long projectId, AddTaskInput input) {
    Task newTask = taskService.addTask(projectId, input.getAssigneeId(), input.getTitle(), input.getDescription(),
        input.getToBeFinishedAtDate());
    return newTask;
  }

  public Task updateTaskState(long taskId, TaskState taskState) {
    return taskService.updateTaskState(taskId, taskState);
  }

}

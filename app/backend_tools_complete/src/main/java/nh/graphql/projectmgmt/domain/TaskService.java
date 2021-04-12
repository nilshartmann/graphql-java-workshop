package nh.graphql.projectmgmt.domain;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nh.graphql.projectmgmt.domain.user.UserService;

@Service
public class TaskService {

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private TaskPublisher taskPublisher;

  @Transactional
  public Task addTask(Long projectId, String assigneeId, String title, String description,
      LocalDateTime toBeFinishedAt) {
    Project project = projectRepository.findById(projectId, false, true).orElseThrow();

    // check userService to make sure user with given assigneeId exists
    userService.getUser(assigneeId).orElseThrow();

    Task task = new Task(assigneeId, title, description, toBeFinishedAt);
    project.addTask(task);

    projectRepository.save(project);

    // publish for Subscriptions
    // NOTE: this is not transactional, but I want to keep this demo as simple as
    // possible
    taskPublisher.publishNewTask(task);
    return task;
  }

  @Transactional
  public Task updateTaskState(Long taskId, TaskState newState) {
    Task task = taskRepository.findById(taskId).orElseThrow();
    task.setState(newState);
    return taskRepository.save(task);
  }
}

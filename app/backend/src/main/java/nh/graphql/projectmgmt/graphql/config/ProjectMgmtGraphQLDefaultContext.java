package nh.graphql.projectmgmt.graphql.config;

import org.dataloader.DataLoaderRegistry;

import graphql.servlet.context.DefaultGraphQLContext;
import nh.graphql.projectmgmt.domain.ProjectRepository;
import nh.graphql.projectmgmt.domain.TaskPublisher;
import nh.graphql.projectmgmt.domain.TaskRepository;
import nh.graphql.projectmgmt.domain.TaskService;
import nh.graphql.projectmgmt.domain.user.UserService;
import nh.graphql.projectmgmt.graphql.ProjectMgmtGraphQLContext;

public class ProjectMgmtGraphQLDefaultContext extends DefaultGraphQLContext implements ProjectMgmtGraphQLContext {

  private UserService userService;
  private ProjectRepository projectRepository;
  private TaskService taskService;
  private TaskRepository taskRepository;
  private TaskPublisher taskPublisher;

  ProjectMgmtGraphQLDefaultContext(ProjectMgmtGraphQLContext contextDelegatee) {
    this(contextDelegatee.getUserService(), contextDelegatee.getProjectRepository(), contextDelegatee.getTaskService(),
        contextDelegatee.getTaskRepository(), contextDelegatee.getTaskPublisher());
  }

  public ProjectMgmtGraphQLDefaultContext(UserService userService, ProjectRepository projectRepository,
      TaskService taskService, TaskRepository taskRepository, TaskPublisher taskPublisher) {
    super(new DataLoaderRegistry(), null);
    this.userService = userService;
    this.projectRepository = projectRepository;
    this.taskService = taskService;
    this.taskRepository = taskRepository;
    this.taskPublisher = taskPublisher;
  }

  public UserService getUserService() {
    return userService;
  }

  public ProjectRepository getProjectRepository() {
    return projectRepository;
  }

  public TaskService getTaskService() {
    return taskService;
  }

  public TaskRepository getTaskRepository() {
    return taskRepository;
  }

  public TaskPublisher getTaskPublisher() {
    return taskPublisher;
  }

}

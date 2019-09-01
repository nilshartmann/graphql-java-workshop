package nh.graphql.projectmgmt.graphql.config;

import nh.graphql.projectmgmt.domain.ProjectRepository;
import nh.graphql.projectmgmt.domain.TaskPublisher;
import nh.graphql.projectmgmt.domain.TaskRepository;
import nh.graphql.projectmgmt.domain.TaskService;
import nh.graphql.projectmgmt.domain.user.UserService;

public interface ProjectMgmtGraphQLContext {

  public UserService getUserService();

  public ProjectRepository getProjectRepository();

  public TaskService getTaskService();

  public TaskRepository getTaskRepository();

  public TaskPublisher getTaskPublisher();

}

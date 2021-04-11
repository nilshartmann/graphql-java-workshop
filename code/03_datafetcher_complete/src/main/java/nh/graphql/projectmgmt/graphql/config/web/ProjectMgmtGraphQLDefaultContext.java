package nh.graphql.projectmgmt.graphql.config.web;

import org.dataloader.DataLoaderRegistry;

import graphql.servlet.context.DefaultGraphQLContext;
import nh.graphql.projectmgmt.domain.ProjectRepository;
import nh.graphql.projectmgmt.domain.TaskPublisher;
import nh.graphql.projectmgmt.domain.TaskRepository;
import nh.graphql.projectmgmt.domain.TaskService;
import nh.graphql.projectmgmt.domain.user.UserService;
import nh.graphql.projectmgmt.graphql.ProjectMgmtGraphQLContext;

public class ProjectMgmtGraphQLDefaultContext extends DefaultGraphQLContext implements ProjectMgmtGraphQLContext {

  private final ProjectMgmtGraphQLContext contextDelegatee;

  public ProjectMgmtGraphQLDefaultContext(ProjectMgmtGraphQLContext contextDelegatee) {
    super(new DataLoaderRegistry(), null);
    this.contextDelegatee = contextDelegatee;
  }

  @Override
  public UserService getUserService() {
    return contextDelegatee.getUserService();
  }

  @Override
  public ProjectRepository getProjectRepository() {
    return contextDelegatee.getProjectRepository();
  }

  @Override
  public TaskService getTaskService() {
    return contextDelegatee.getTaskService();
  }

  @Override
  public TaskRepository getTaskRepository() {
    return contextDelegatee.getTaskRepository();
  }

  @Override
  public TaskPublisher getTaskPublisher() {
    return contextDelegatee.getTaskPublisher();
  }
}

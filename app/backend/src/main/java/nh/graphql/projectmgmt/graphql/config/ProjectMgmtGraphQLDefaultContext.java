package nh.graphql.projectmgmt.graphql.config;

import org.dataloader.DataLoaderRegistry;

import graphql.servlet.context.DefaultGraphQLContext;
import nh.graphql.projectmgmt.domain.ProjectRepository;
import nh.graphql.projectmgmt.domain.TaskPublisher;
import nh.graphql.projectmgmt.domain.TaskRepository;
import nh.graphql.projectmgmt.domain.TaskService;
import nh.graphql.projectmgmt.domain.user.UserService;

public class ProjectMgmtGraphQLDefaultContext extends DefaultGraphQLContext implements ProjectMgmtGraphQLContext {

  private ProjectMgmtGraphQLContext contextDelegatee;

  public ProjectMgmtGraphQLDefaultContext(ProjectMgmtGraphQLContext contextDelegatee) {
    super(new DataLoaderRegistry(), null);
    this.contextDelegatee = contextDelegatee;
  }

  public UserService getUserService() {
    return contextDelegatee.getUserService();
  }

  public ProjectRepository getProjectRepository() {
    return contextDelegatee.getProjectRepository();
  }

  public TaskService getTaskService() {
    return contextDelegatee.getTaskService();
  }

  public TaskRepository getTaskRepository() {
    return contextDelegatee.getTaskRepository();
  }

  public TaskPublisher getTaskPublisher() {
    return contextDelegatee.getTaskPublisher();
  }

}

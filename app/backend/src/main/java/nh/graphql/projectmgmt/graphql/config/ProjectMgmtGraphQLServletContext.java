package nh.graphql.projectmgmt.graphql.config;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.dataloader.DataLoaderRegistry;

import graphql.servlet.context.DefaultGraphQLContext;
import graphql.servlet.context.GraphQLServletContext;
import nh.graphql.projectmgmt.domain.ProjectRepository;
import nh.graphql.projectmgmt.domain.TaskPublisher;
import nh.graphql.projectmgmt.domain.TaskRepository;
import nh.graphql.projectmgmt.domain.TaskService;
import nh.graphql.projectmgmt.domain.user.UserService;
import nh.graphql.projectmgmt.graphql.ProjectMgmtGraphQLContext;

/**
 * Enhanced copy of from graphql.servlet.context.DefaultGraphQLServletContext
 */
class ProjectMgmtGraphQLServletContext extends DefaultGraphQLContext
    implements GraphQLServletContext, ProjectMgmtGraphQLContext {
  private final ProjectMgmtGraphQLContext projectMgmtGraphQLContext;

  private final HttpServletRequest httpServletRequest;
  private final HttpServletResponse httpServletResponse;

  ProjectMgmtGraphQLServletContext(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
      ProjectMgmtGraphQLContext projectMgmtGraphQLContext) {
    super(new DataLoaderRegistry(), null);
    this.projectMgmtGraphQLContext = projectMgmtGraphQLContext;
    this.httpServletRequest = httpServletRequest;
    this.httpServletResponse = httpServletResponse;
  }

  @Override
  public HttpServletRequest getHttpServletRequest() {
    return httpServletRequest;
  }

  @Override
  public HttpServletResponse getHttpServletResponse() {
    return httpServletResponse;
  }

  @Override
  public List<Part> getFileParts() {
    try {
      return httpServletRequest.getParts().stream().filter(part -> part.getContentType() != null)
          .collect(Collectors.toList());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Map<String, List<Part>> getParts() {
    try {
      return httpServletRequest.getParts().stream().collect(Collectors.groupingBy(Part::getName));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public UserService getUserService() {
    return projectMgmtGraphQLContext.getUserService();
  }

  public ProjectRepository getProjectRepository() {
    return projectMgmtGraphQLContext.getProjectRepository();
  }

  public TaskService getTaskService() {
    return projectMgmtGraphQLContext.getTaskService();
  }

  public TaskRepository getTaskRepository() {
    return projectMgmtGraphQLContext.getTaskRepository();
  }

  public TaskPublisher getTaskPublisher() {
    return projectMgmtGraphQLContext.getTaskPublisher();
  }

}

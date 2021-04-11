package nh.graphql.projectmgmt.graphql.config.web;

import java.util.Optional;

import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;

import org.dataloader.DataLoaderRegistry;

import graphql.servlet.context.DefaultGraphQLContext;
import graphql.servlet.context.GraphQLWebSocketContext;
import graphql.servlet.core.ApolloSubscriptionConnectionListener;
import nh.graphql.projectmgmt.domain.ProjectRepository;
import nh.graphql.projectmgmt.domain.TaskPublisher;
import nh.graphql.projectmgmt.domain.TaskRepository;
import nh.graphql.projectmgmt.domain.TaskService;
import nh.graphql.projectmgmt.domain.user.UserService;
import nh.graphql.projectmgmt.graphql.ProjectMgmtGraphQLContext;

/**
 * Copied and enhanced from
 * graphql.servlet.context.DefaultGraphQLWebSocketContext
 */
class ProjectMgmtGraphQLWebSocketContext extends DefaultGraphQLContext
    implements GraphQLWebSocketContext, ProjectMgmtGraphQLContext {
  private final ProjectMgmtGraphQLContext projectContextDelegatee;
  private final Session session;
  private final HandshakeRequest handshakeRequest;

  ProjectMgmtGraphQLWebSocketContext(Session session, HandshakeRequest handshakeRequest,
      ProjectMgmtGraphQLContext projectContextDelegatee) {
    super(new DataLoaderRegistry(), null);
    this.projectContextDelegatee = projectContextDelegatee;
    this.session = session;
    this.handshakeRequest = handshakeRequest;
  }

  @Override
  public Session getSession() {
    return session;
  }

  @Override
  public Optional<Object> getConnectResult() {
    return Optional.of(session)
        .map(session -> session.getUserProperties().get(ApolloSubscriptionConnectionListener.CONNECT_RESULT_KEY));
  }

  @Override
  public HandshakeRequest getHandshakeRequest() {
    return handshakeRequest;
  }

  @Override
  public UserService getUserService() {
    return projectContextDelegatee.getUserService();
  }

  @Override
  public ProjectRepository getProjectRepository() {
    return projectContextDelegatee.getProjectRepository();
  }

  @Override
  public TaskService getTaskService() {
    return projectContextDelegatee.getTaskService();
  }

  @Override
  public TaskRepository getTaskRepository() {
    return projectContextDelegatee.getTaskRepository();
  }

  @Override
  public TaskPublisher getTaskPublisher() {
    return projectContextDelegatee.getTaskPublisher();
  }
}

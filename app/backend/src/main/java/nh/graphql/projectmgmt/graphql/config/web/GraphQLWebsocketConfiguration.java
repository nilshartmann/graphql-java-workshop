package nh.graphql.projectmgmt.graphql.config.web;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.Lifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.standard.ServerEndpointRegistration;

import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLWebsocketServlet;
import graphql.servlet.config.DefaultGraphQLSchemaProvider;
import graphql.servlet.core.GraphQLObjectMapper;
import graphql.servlet.core.GraphQLQueryInvoker;
import graphql.servlet.input.GraphQLInvocationInputFactory;

@Configuration
public class GraphQLWebsocketConfiguration {

  @Bean
  public ServerEndpointRegistration serverEndpointRegistration(GraphQLSchema schema,
      ProjectMgmtGraphQLContextBuilder projectGraphQLContextBuilder) {
    DefaultGraphQLSchemaProvider schemaProvider = new DefaultGraphQLSchemaProvider(schema);
    GraphQLQueryInvoker queryInvoker = GraphQLQueryInvoker.newBuilder().build();
    GraphQLInvocationInputFactory invocationInputFactory = GraphQLInvocationInputFactory.newBuilder(schemaProvider) //
        .withGraphQLContextBuilder(projectGraphQLContextBuilder) //
        .build();

    // create servlet
    final GraphQLWebsocketServlet websocketServlet = new GraphQLWebsocketServlet(queryInvoker, invocationInputFactory,
        GraphQLObjectMapper.newBuilder().build());

    //
    return new GraphQLWsServerEndpointRegistration("/subscriptions", websocketServlet);
  }

  @Bean
  @ConditionalOnMissingBean
  @Profile("!withoutWebSocket")
  public ServerEndpointExporter serverEndpointExporter() {
    return new ServerEndpointExporter();
  }

  class GraphQLWsServerEndpointRegistration extends ServerEndpointRegistration implements Lifecycle {

    private final GraphQLWebsocketServlet servlet;

    public GraphQLWsServerEndpointRegistration(String path, GraphQLWebsocketServlet servlet) {
      super(path, servlet);
      this.servlet = servlet;
    }

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
      super.modifyHandshake(sec, request, response);
      servlet.modifyHandshake(sec, request, response);
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
      servlet.beginShutDown();
    }

    @Override
    public boolean isRunning() {
      return !servlet.isShutDown();
    }
  }

}

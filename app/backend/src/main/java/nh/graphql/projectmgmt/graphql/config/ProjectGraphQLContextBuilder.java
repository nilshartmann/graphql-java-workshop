package nh.graphql.projectmgmt.graphql.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;

import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.servlet.context.DefaultGraphQLContextBuilder;
import graphql.servlet.context.GraphQLContext;
import nh.graphql.projectmgmt.graphql.fetcher.ProjectDataFetchers;

@Component
public class ProjectGraphQLContextBuilder extends DefaultGraphQLContextBuilder {

  @Autowired
  private ProjectDataFetchers projectDataFetchers;

  @Override
  public GraphQLContext build(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    GraphQLContext context = super.build(httpServletRequest, httpServletResponse);
    addDataLoaders(context);
    return context;
  }

  @Override
  public GraphQLContext build(Session session, HandshakeRequest handshakeRequest) {
    GraphQLContext context = super.build(session, handshakeRequest);
    addDataLoaders(context);
    return context;
  }

  @Override
  public GraphQLContext build() {
    GraphQLContext context = super.build();
    addDataLoaders(context);
    return context;
  }

  private void addDataLoaders(GraphQLContext context) {
    DataLoaderRegistry dataLoaderRegistry = context.getDataLoaderRegistry().orElseThrow();
    dataLoaderRegistry.register("userDataLoader", DataLoader.newDataLoader(projectDataFetchers.userBatchLoader));
  }

}
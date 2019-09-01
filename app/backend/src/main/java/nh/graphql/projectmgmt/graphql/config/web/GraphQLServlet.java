package nh.graphql.projectmgmt.graphql.config.web;

import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;

import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLHttpServlet;
import graphql.servlet.config.GraphQLConfiguration;

@WebServlet(name = "GraphQLServlet", urlPatterns = { "/graphql" }, loadOnStartup = 1)
public class GraphQLServlet extends GraphQLHttpServlet {

  private static final long serialVersionUID = 1L;

  @Autowired
  private GraphQLSchema schema;

  @Autowired
  private ProjectMgmtGraphQLContextBuilder projectGraphQLContextBuilder;

  @Override
  protected GraphQLConfiguration getConfiguration() {
    return GraphQLConfiguration //
        .with(schema) //
        .with(projectGraphQLContextBuilder) //
        .build();
  }
}

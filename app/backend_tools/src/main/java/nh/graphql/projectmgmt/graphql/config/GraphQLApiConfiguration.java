package nh.graphql.projectmgmt.graphql.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.coxautodev.graphql.tools.SchemaParser;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import nh.graphql.projectmgmt.graphql.fetcher.MutationResolver;
import nh.graphql.projectmgmt.graphql.fetcher.ProjectResolver;
import nh.graphql.projectmgmt.graphql.fetcher.QueryResolver;
import nh.graphql.projectmgmt.graphql.fetcher.SubscriptionResolver;
import nh.graphql.projectmgmt.graphql.fetcher.TaskResolver;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */

@Configuration
public class GraphQLApiConfiguration {

  private final static Logger logger = LoggerFactory.getLogger(GraphQLApiConfiguration.class);

  @Bean
  public GraphQLSchema graphQLSchema(MutationResolver mutationResolver, ProjectResolver projectResolver,
      QueryResolver queryResolver, TaskResolver taskResolver, SubscriptionResolver subscriptionResolver)
      throws IOException {
    logger.info("Building GraphQL Schema");

    String schemaString = readSchema("/projectmgmt.graphqls");

    // SchemaParser from graphql-java-tools !
    SchemaParser schemaParser = SchemaParser.newParser() //
        .schemaString(schemaString) //
        .resolvers(mutationResolver, projectResolver, queryResolver, taskResolver, subscriptionResolver) //
        .build();

    return schemaParser.makeExecutableSchema();
  }

  @Bean
  public GraphQL graphql(GraphQLSchema graphQLSchema) {
    return GraphQL.newGraphQL(graphQLSchema).build();
  }

  private String readSchema(String fileName) throws IOException {

    InputStream inputStream = getClass().getResourceAsStream(fileName);
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    int nRead;
    byte[] data = new byte[1024];
    while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
      buffer.write(data, 0, nRead);
    }

    buffer.flush();
    byte[] byteArray = buffer.toByteArray();

    String text = new String(byteArray, StandardCharsets.UTF_8);
    return text;
  }
}

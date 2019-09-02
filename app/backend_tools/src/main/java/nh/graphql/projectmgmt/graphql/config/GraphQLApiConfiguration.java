package nh.graphql.projectmgmt.graphql.config;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

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
import graphql.schema.idl.RuntimeWiring;
import nh.graphql.projectmgmt.graphql.fetcher.MutationFetchers;
import nh.graphql.projectmgmt.graphql.fetcher.MutationResolver;
import nh.graphql.projectmgmt.graphql.fetcher.ProjectDataFetchers;
import nh.graphql.projectmgmt.graphql.fetcher.ProjectResolver;
import nh.graphql.projectmgmt.graphql.fetcher.QueryDataFetchers;
import nh.graphql.projectmgmt.graphql.fetcher.QueryResolver;
import nh.graphql.projectmgmt.graphql.fetcher.SubscriptionFetchers;
import nh.graphql.projectmgmt.graphql.fetcher.SubscriptionResolver;
import nh.graphql.projectmgmt.graphql.fetcher.TaskFetchers;
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

    GraphQLSchema makeExecutableSchema = schemaParser.makeExecutableSchema();
    logger.info("Support isSupportingSubscriptions {}", makeExecutableSchema.isSupportingSubscriptions());
    return makeExecutableSchema;
//    
//
////    SchemaParser schemaParser = new SchemaParser();
//    TypeDefinitionRegistry typeRegistry = schemaParser.parse(new InputStreamReader(inputStream));
//
//    RuntimeWiring runtimeWiring = setupWiring();
//
//    SchemaGenerator schemaGenerator = new SchemaGenerator();
//    return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
  }

  private RuntimeWiring setupWiring() {

    QueryDataFetchers queryDataFetchers = new QueryDataFetchers();
    ProjectDataFetchers projectDataFetchers = new ProjectDataFetchers();
    TaskFetchers taskFetchers = new TaskFetchers();
    MutationFetchers mutationFetchers = new MutationFetchers();
    SubscriptionFetchers subscriptionFetcher = new SubscriptionFetchers();

    RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring() //
        .type(newTypeWiring("Query") //
            .dataFetcher("ping", queryDataFetchers.ping) //
            .dataFetcher("users", queryDataFetchers.users) //
            .dataFetcher("user", queryDataFetchers.user) //
            .dataFetcher("projects", queryDataFetchers.projects) //
            .dataFetcher("project", queryDataFetchers.projectById)) //
        .type(newTypeWiring("Mutation") //
            .dataFetcher("updateTaskState", mutationFetchers.updateTaskState) //
            .dataFetcher("addTask", mutationFetchers.addTask))
        .type(newTypeWiring("Subscription") //
            .dataFetcher("onNewTask", subscriptionFetcher.onNewTask)) //
        .type(newTypeWiring("Project") //
            .dataFetcher("task", projectDataFetchers.task) //
            .dataFetcher("owner", projectDataFetchers.owner))
        .type(newTypeWiring("Task") //
            .dataFetcher("assignee", taskFetchers.assignee)) //
        .build();

    return runtimeWiring;
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

package nh.graphql.projectmgmt.graphql.config;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import nh.graphql.projectmgmt.graphql.fetcher.MutationFetchers;
import nh.graphql.projectmgmt.graphql.fetcher.ProjectDataFetchers;
import nh.graphql.projectmgmt.graphql.fetcher.QueryDataFetchers;
import nh.graphql.projectmgmt.graphql.fetcher.SubscriptionFetchers;
import nh.graphql.projectmgmt.graphql.fetcher.TaskFetchers;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */

@Configuration
public class GraphQLApiConfiguration {

  private final static Logger logger = LoggerFactory.getLogger(GraphQLApiConfiguration.class);

  @Bean
  public GraphQLSchema graphQLSchema() {
    logger.info("Building GraphQL Schema");

    SchemaParser schemaParser = new SchemaParser();
    InputStream inputStream = getClass().getResourceAsStream("/projectmgmt.graphqls");
    TypeDefinitionRegistry typeRegistry = schemaParser.parse(new InputStreamReader(inputStream));

    RuntimeWiring runtimeWiring = setupWiring();

    SchemaGenerator schemaGenerator = new SchemaGenerator();
    return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
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
}

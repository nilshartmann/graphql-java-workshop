package nh.graphql.tasks.graphql;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import nh.graphql.tasks.graphql.fetcher.MutationFetchers;
import nh.graphql.tasks.graphql.fetcher.ProjectDataFetchers;
import nh.graphql.tasks.graphql.fetcher.QueryDataFetchers;
import nh.graphql.tasks.graphql.fetcher.SubscriptionFetchers;
import nh.graphql.tasks.graphql.fetcher.TaskFetchers;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */

@Component
public class GraphQLApi {

  private final static Logger logger = LoggerFactory.getLogger(GraphQLApi.class);

  @Autowired
  private QueryDataFetchers queryDataFetchers;
  @Autowired
  private ProjectDataFetchers projectDataFetchers;
  @Autowired
  private TaskFetchers taskFetchers;
  @Autowired
  private MutationFetchers mutationFetchers;
  @Autowired
  private SubscriptionFetchers subscriptionFetcher;

  @Bean
  public GraphQLSchema graphQLSchema() {
    logger.info("Building GraphQL Schema");

    SchemaParser schemaParser = new SchemaParser();
    InputStream inputStream = getClass().getResourceAsStream("/tasks.graphqls");
    TypeDefinitionRegistry typeRegistry = schemaParser.parse(new InputStreamReader(inputStream));

    RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring() //
        .type(newTypeWiring("Query") //
            .dataFetcher("ping",
                env -> "Hello, World @ " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"))) //
            .dataFetcher("users", queryDataFetchers.users) //
            .dataFetcher("user", queryDataFetchers.user) //
            .dataFetcher("projects", queryDataFetchers.projects) //
            .dataFetcher("project", queryDataFetchers.project)) //
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

    SchemaGenerator schemaGenerator = new SchemaGenerator();
    return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
  }

  @Bean
  public GraphQL graphql(GraphQLSchema graphQLSchema) {
    return GraphQL.newGraphQL(graphQLSchema).build();
  }

//  @Bean
//  public DataLoaderRegistry configureDataLoader(Optional<DataLoaderRegistry> dataLoaderRegistryCandidate) {
//    final DataLoaderRegistry dataLoaderRegistry = dataLoaderRegistryCandidate.orElse(new DataLoaderRegistry());
//    DataLoader userDataLoader = DataLoader.newDataLoader(ratingDataFetchers.userBatchLoader);
//    dataLoaderRegistry.register("user", userDataLoader);
//
//    return dataLoaderRegistry;
//  }

}

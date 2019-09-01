package nh.graphql.projectmgmt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import nh.graphql.projectmgmt.domain.ProjectRepository;
import nh.graphql.projectmgmt.domain.TaskPublisher;
import nh.graphql.projectmgmt.domain.TaskRepository;
import nh.graphql.projectmgmt.domain.TaskService;
import nh.graphql.projectmgmt.domain.user.UserService;
import nh.graphql.projectmgmt.graphql.config.ProjectMgmtGraphQLContext;
import nh.graphql.projectmgmt.graphql.config.ProjectMgmtGraphQLDefaultContext;

// @SpringBootApplication
public class QueryRunnerApplication {

  private static final Logger logger = LoggerFactory.getLogger(QueryRunnerApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(QueryRunnerApplication.class, args);
  }

  @Bean
  public CommandLineRunner runQuery(Importer importer, GraphQLSchema graphQLSchema, final UserService userService,
      ProjectRepository projectRepository, final TaskService taskService, final TaskRepository taskRepository,
      final TaskPublisher taskPublisher) {

    return args -> {
      logger.info("====== IMPORTING DATA ======= ");
      importer.add();
      logger.info("====== RUNNING QUERY ======= ");

      GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();

      ExecutionInput executionInput = ExecutionInput.newExecutionInput().query("query { users { name } }")
          .context(new ProjectMgmtGraphQLDefaultContext(new ProjectMgmtGraphQLContext() {
            @Override
            public UserService getUserService() {
              return userService;
            }

            @Override
            public TaskService getTaskService() {
              return taskService;
            }

            @Override
            public TaskRepository getTaskRepository() {
              return taskRepository;
            }

            @Override
            public TaskPublisher getTaskPublisher() {
              return taskPublisher;
            }

            @Override
            public ProjectRepository getProjectRepository() {
              return projectRepository;
            }
          })).build();

      ExecutionResult executionResult = graphQL.execute(executionInput);

      Object data = executionResult.getData();
      logger.info("QUERY RESULT {}", data);

    };

  }

}

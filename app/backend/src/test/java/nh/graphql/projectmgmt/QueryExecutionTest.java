package nh.graphql.projectmgmt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import nh.graphql.projectmgmt.domain.ProjectRepository;
import nh.graphql.projectmgmt.domain.TaskPublisher;
import nh.graphql.projectmgmt.domain.TaskRepository;
import nh.graphql.projectmgmt.domain.TaskService;
import nh.graphql.projectmgmt.domain.user.UserService;
import nh.graphql.projectmgmt.graphql.ProjectMgmtGraphQLContext;
import nh.graphql.projectmgmt.graphql.config.ProjectMgmtGraphQLDefaultContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("withoutWebSocket")
public class QueryExecutionTest {

  private static final Logger logger = LoggerFactory.getLogger(QueryExecutionTest.class);

  @Autowired
  private GraphQLSchema graphQLSchema;

  @Autowired
  private UserService userService;
  @Autowired
  private ProjectRepository projectRepository;
  @Autowired
  private TaskService taskService;
  @Autowired
  private TaskRepository taskRepository;
  @Autowired
  private TaskPublisher taskPublisher;

  @Test
  public void executeQuery() throws Exception {
    ProjectMgmtGraphQLContext context = new ProjectMgmtGraphQLDefaultContext(userService, projectRepository,
        taskService, taskRepository, taskPublisher);

//
    GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();

    ExecutionInput executionInput = ExecutionInput.newExecutionInput().query("query { users { name } }")
        .context(context).build();

    ExecutionResult executionResult = graphQL.execute(executionInput);
//
    Object data = executionResult.getData();
    logger.info("QUERY RESULT {}", data);

  }

}

package nh.graphql.projectmgmt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import nh.graphql.projectmgmt.domain.user.User;
import nh.graphql.projectmgmt.domain.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ProjectMgmtApplicationTests extends AbstractGraphQLTest {

  private static final Logger logger = LoggerFactory.getLogger(ProjectMgmtApplicationTests.class);

  @Autowired
  Importer importer;

  @Test
  public void ping() throws JsonProcessingException {
    GraphQLTestResponse graphQLTestResponse = execute("query {ping}");
    assertThat(graphQLTestResponse.get("$.data.ping")).startsWith("Hello, World");
  }

  @Test
  public void projects() throws JsonProcessingException {
    GraphQLTestResponse graphQLTestResponse = execute("query {projects{id}}");
    assertThat(graphQLTestResponse.getInt("$.data.projects.length()")).isEqualTo(6);
  }

  @Test
  public void projectTasks() throws JsonProcessingException {
    GraphQLTestResponse graphQLTestResponse = execute("query {project(id:1){id tasks { title }}}");
    assertThat(graphQLTestResponse.getInt("$.data.project.tasks.length()")).isGreaterThanOrEqualTo(3);
  }

  @Test
  public void addTask() {
      String mutation = loadGraphQL("addTaskMutation");
      GraphQLTestResponse graphQLTestResponse = execute(mutation);
      assertThat(graphQLTestResponse.get("$.data.addTask.title")).isEqualTo("New Task");
      assertThat(graphQLTestResponse.get("$.data.addTask.description")).isEqualTo("New Description");
      assertThat(graphQLTestResponse.get("$.data.addTask.toBeFinishedAt")).isNotBlank();
  }

  @Test
  public void projectsPageQuery() {
    String query = loadGraphQL("ProjectsPageQuery");
    GraphQLTestResponse graphQLTestResponse = execute(query);
    assertThat(graphQLTestResponse.getInt("$.data.projects.length()")).isEqualTo(6);
  }

  @Test
  public void projectsPageQueryWithDataLoader() {
    String query = loadGraphQL("ProjectsPageQueryWithDataLoader");
    GraphQLTestResponse graphQLTestResponse = execute(query);
    assertThat(graphQLTestResponse.getInt("$.data.projects.length()")).isEqualTo(6);
  }

  @Test
  public void allUsersQuery() {
    String query = loadGraphQL("AllUsersQuery");
    GraphQLTestResponse graphQLTestResponse = execute(query);
    assertThat(graphQLTestResponse.getInt("$.data.users.length()")).isEqualTo(8);
  }
}

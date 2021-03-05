package nh.graphql.projectmgmt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import nh.graphql.projectmgmt.domain.user.User;
import nh.graphql.projectmgmt.domain.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProjectMgmtApplicationToolsTests {

  private static final Logger logger = LoggerFactory.getLogger(ProjectMgmtApplicationToolsTests.class);

  @Autowired
  private ResourceLoader resourceLoader;

  @Autowired
  TestRestTemplate restTemplate;

  @Autowired
  Importer importer;

  private ObjectMapper objectMapper = new ObjectMapper();

  @MockBean
  UserService userService;

  private static User mockUser(String id) {
    User user = new User(id, "LOGIN_" + id, "NAME_" + id);
    return user;
  }

  @Before
  public void configureUserServiceMock() {
    when(userService.getUser(anyString())).thenAnswer(
      new Answer() {
        public Object answer(InvocationOnMock invocation) {
          Object[] args = invocation.getArguments();
          return Optional.of(mockUser((String) args[0]));
        }
      });

    final List<User> allUsers = new LinkedList<>();
    allUsers.add(new User("U1", "nils", "Nils Hartmann"));
    allUsers.add(new User("U2", "susi", "Susi Mueller"));
    allUsers.add(new User("U3", "klaus", "Klaus Schneider"));
    allUsers.add(new User("U4", "sue", "Sue Taylor"));
    allUsers.add(new User("U5", "lauren", "Lauren Jones"));
    allUsers.add(new User("U6", "olivia", "Olivia Smith"));
    allUsers.add(new User("U7", "cathy", "Cath Brown"));
    allUsers.add(new User("U8", "maja", "Maja Walsh"));

    when(userService.getAllUsers()).thenReturn(allUsers);
  }


  @Test
  public void ping() throws JsonProcessingException {
    GraphQLTestResponse graphQLTestResponse = execute("query {ping}");
    assertThat(graphQLTestResponse.get("$.data.ping")).startsWith("Hello, World");
  }

  @Test
  public void projectTasks() throws JsonProcessingException {
    GraphQLTestResponse graphQLTestResponse = execute("query {project(id:1){id tasks { title }}}");
    assertThat(graphQLTestResponse.getInt("$.data.project.tasks.length()")).isGreaterThanOrEqualTo(1);
  }

  @Test
  public void addTask() {
      String mutation = loadGraphQL("addTaskMutation");
      GraphQLTestResponse graphQLTestResponse = execute(mutation);
      assertThat(graphQLTestResponse.get("$.data.addTask.title")).isEqualTo("New Task");
      assertThat(graphQLTestResponse.get("$.data.addTask.description")).isEqualTo("New Description");
      assertThat(graphQLTestResponse.get("$.data.addTask.toBeFinishedAt")).isNotBlank();
  }


  private String loadGraphQL(String name) {
    String resourceName = getClass().getPackageName().replace('.', '/') + "/" + name + ".graphQL";
    Resource resource = resourceLoader.getResource("classpath:" + resourceName);
    try (InputStream inputStream = resource.getInputStream()) {
      return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
    } catch (Exception ex) {
      throw new IllegalStateException("Could not load resource '" + resourceName + "' from classpath: " + ex, ex);
    }

  }

  private GraphQLTestResponse execute(String graphqlQuery) {
    String jsonRequest = createJsonQuery(graphqlQuery);
    HttpEntity<Object> httpEntity = forJson(jsonRequest);

    ResponseEntity<String> response = restTemplate.exchange("/graphql", HttpMethod.POST, httpEntity, String.class);
    GraphQLTestResponse graphQLTestResponse = new GraphQLTestResponse(response.getStatusCodeValue(),
      response.getBody());
    logger.info("GRAPHQL RESPONSE ====>>> {}", graphQLTestResponse);

    return graphQLTestResponse;
  }

  // ALL FROM
  // https://github.com/graphql-java-kickstart/graphql-spring-boot/blob/master/graphql-spring-boot-test/src/main/java/com/graphql/spring/boot/test
  private String createJsonQuery(String graphql) {

    ObjectNode wrapper = objectMapper.createObjectNode();
    wrapper.put("query", graphql);
//  wrapper.set("variables", variables);
    try {
      return objectMapper.writeValueAsString(wrapper);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Could not create json: " + e, e);
    }
  }

  static HttpEntity<Object> forJson(String json) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    return new HttpEntity<>(json, headers);
  }

}

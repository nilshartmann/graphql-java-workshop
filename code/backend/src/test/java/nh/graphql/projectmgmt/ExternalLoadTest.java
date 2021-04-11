package nh.graphql.projectmgmt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

public class ExternalLoadTest {

    private static final Logger logger = LoggerFactory.getLogger(ExternalLoadTest.class);

    private String mutationTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    private final TestRestTemplate restTemplate = new TestRestTemplate();


    @Before
    public void loadMutationTemplate() throws Exception {
        this.mutationTemplate = loadGraphQL("addTaskMutationWithPlaceholders");
    }


    private void addTask(String projectId, String task, String assigneeId) {
        String taskTitle = "Task " + task;
        String taskDescription = "Description for Task " + task;
        String mutation = this.mutationTemplate
            .replace("__PROJECT_ID__", projectId)
            .replace("__TITLE__", taskTitle)
            .replace("__DESCRIPTION__", taskDescription)
            .replace("__ASSIGNEE_ID__", assigneeId);

        logger.info("Creating Task '{}' for Project '{}", taskTitle, projectId);

        GraphQLTestResponse graphQLTestResponse = execute(mutation);
        assertThat(graphQLTestResponse.isOk()).isTrue();
        assertThat(graphQLTestResponse.get("$.data.addTask.title")).isEqualTo(taskTitle);
        assertThat(graphQLTestResponse.get("$.data.addTask.description")).isEqualTo(taskDescription);
        assertThat(graphQLTestResponse.get("$.data.addTask.toBeFinishedAt")).isNotBlank();
    }


    private CompletableFuture<Void> createTasks(final String projectId, final String assigneeId) {
        return CompletableFuture.supplyAsync(() -> {

            for (int i = 0; i < 10; i++) {
                addTask(projectId, ""+i, assigneeId);
                try {
                    Thread.sleep(randomLong());
                } catch (Exception ex) {
                    ex.printStackTrace();

                }

            }
            return null;
        });
    }


    @Test
    @Ignore("Can only be run with running userservice")
    public void addTasks() throws Exception {
        CompletableFuture<Void> combinedFuture
            = CompletableFuture.allOf(
            createTasks("1", "U1"),
            createTasks("2", "U2"),
            createTasks("3", "U3"),
            createTasks("3", "U4"),
            createTasks("2", "U2"),
            createTasks("1", "U2"));

        combinedFuture.get();
    }

    public static long randomLong() {
        final int min = 50;
        final int max = 250;

        Random r = new Random();

        return r.nextInt((max - min) + 1) + min;
    }

    protected String loadGraphQL(String name) throws Exception {
        String resourceName = getClass().getPackageName().replace('.', '/') + "/" + name + ".graphQL";

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new IllegalStateException("Could not load resource '" + resourceName + "' from classpath: " + ex, ex);
        }
    }


    protected GraphQLTestResponse execute(String graphqlQuery) {
        String jsonRequest = createJsonQuery(graphqlQuery);


        HttpEntity<Object> httpEntity = forJson(jsonRequest);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:5000/graphql", HttpMethod.POST, httpEntity, String.class);
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

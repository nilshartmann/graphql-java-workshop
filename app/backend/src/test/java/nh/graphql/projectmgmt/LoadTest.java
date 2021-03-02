package nh.graphql.projectmgmt;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

public class LoadTest extends AbstractGraphQLTest {

    private static final Logger logger = LoggerFactory.getLogger(LoadTest.class);

    @Autowired
    Importer importer;

    private String mutationTemplate;


    @Before
    public void loadMutationTemplate() {
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
}

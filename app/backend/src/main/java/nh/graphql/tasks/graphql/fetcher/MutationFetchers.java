package nh.graphql.tasks.graphql.fetcher;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import nh.graphql.tasks.domain.Task;
import nh.graphql.tasks.domain.TaskService;
import nh.graphql.tasks.domain.TaskState;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class MutationFetchers {
  private final static Logger logger = LoggerFactory.getLogger(MutationFetchers.class);

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS[xxx][xx][X]");

  @Autowired
  private TaskService taskService;

  public DataFetcher<Task> addTask = new DataFetcher<>() {
    @Override
    public Task get(DataFetchingEnvironment environment) throws Exception {
      long projectId = Long.parseLong(environment.getArgument("projectId"));
      Map<String, String> input = environment.getArgument("input");

      String title = input.get("title");
      String description = input.get("description");

      String toBeFinishedAtInput = input.get("toBeFinishedAt");
      LocalDateTime toBeFinishedAt = toBeFinishedAtInput != null ? LocalDateTime.parse(toBeFinishedAtInput, formatter)
          : LocalDateTime.now().plusDays(14);

      String assigneeId = input.get("assigneeId");

      Task newTask = taskService.addTask(projectId, assigneeId, title, description, toBeFinishedAt);
      return newTask;
    }
  };

  public DataFetcher<Task> updateTaskState = new DataFetcher<Task>() {
    @Override
    public Task get(DataFetchingEnvironment environment) throws Exception {
      long taskId = Long.parseLong(environment.getArgument("taskId"));
      String newStateString = environment.getArgument("newState");
      TaskState newState = TaskState.valueOf(newStateString);

      return taskService.updateTaskState(taskId, newState);
    }
  };
}

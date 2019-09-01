package nh.graphql.projectmgmt.graphql.fetcher;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import nh.graphql.projectmgmt.domain.Task;
import nh.graphql.projectmgmt.domain.TaskState;
import nh.graphql.projectmgmt.graphql.ProjectMgmtGraphQLContext;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class MutationFetchers {
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS[xxx][xx][X]");

  public DataFetcher<Task> addTask = new DataFetcher<>() {
    @Override
    public Task get(DataFetchingEnvironment environment) throws Exception {
      ProjectMgmtGraphQLContext context = environment.getContext();

      long projectId = Long.parseLong(environment.getArgument("projectId"));
      Map<String, String> input = environment.getArgument("input");
      String title = input.get("title");
      String description = input.get("description");
      String toBeFinishedAtInput = input.get("toBeFinishedAt");
      LocalDateTime toBeFinishedAt = toBeFinishedAtInput != null && toBeFinishedAtInput.length() > 0
          ? LocalDateTime.parse(toBeFinishedAtInput, formatter)
          : LocalDateTime.now().plusDays(14);
      String assigneeId = input.get("assigneeId");

      Task newTask = context.getTaskService().addTask(projectId, assigneeId, title, description, toBeFinishedAt);
      return newTask;
    }
  };

  public DataFetcher<Task> updateTaskState = new DataFetcher<Task>() {
    @Override
    public Task get(DataFetchingEnvironment environment) throws Exception {
      ProjectMgmtGraphQLContext context = environment.getContext();
      long taskId = Long.parseLong(environment.getArgument("taskId"));
      String newStateString = environment.getArgument("newState");
      TaskState newState = TaskState.valueOf(newStateString);

      return context.getTaskService().updateTaskState(taskId, newState);
    }
  };
}

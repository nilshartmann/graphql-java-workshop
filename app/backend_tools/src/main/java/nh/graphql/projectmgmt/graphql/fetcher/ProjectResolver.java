package nh.graphql.projectmgmt.graphql.fetcher;

import java.util.concurrent.CompletableFuture;

import graphql.kickstart.tools.GraphQLResolver;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import graphql.schema.DataFetchingEnvironment;
import nh.graphql.projectmgmt.domain.Project;
import nh.graphql.projectmgmt.domain.Task;
import nh.graphql.projectmgmt.domain.TaskRepository;
import nh.graphql.projectmgmt.domain.user.User;

@Component
public class ProjectResolver implements GraphQLResolver<Project> {
  @Autowired
  private TaskRepository taskRepository;

  public Task task(Project source, long taskId) {
    return taskRepository.findById(taskId).orElse(null);
  }

  public CompletableFuture<User> owner(Project project, DataFetchingEnvironment environment) {
    String userId = project.getOwnerId();
    DataLoader<String, User> dataLoader = environment.getDataLoader("userDataLoader");
    return dataLoader.load(userId);
  }

}

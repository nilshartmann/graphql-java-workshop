package nh.graphql.projectmgmt.graphql.fetcher;

import java.util.concurrent.CompletableFuture;

import graphql.kickstart.tools.GraphQLResolver;
import org.dataloader.DataLoader;
import org.springframework.stereotype.Component;


import graphql.schema.DataFetchingEnvironment;
import nh.graphql.projectmgmt.domain.Task;
import nh.graphql.projectmgmt.domain.user.User;

@Component
public class TaskResolver implements GraphQLResolver<Task> {

  public CompletableFuture<User> assignee(Task source, DataFetchingEnvironment environment) {
    String userId = source.getAssigneeId();

    DataLoader<String, User> dataLoader = environment.getDataLoader("userDataLoader");
    return dataLoader.load(userId);
  }

}

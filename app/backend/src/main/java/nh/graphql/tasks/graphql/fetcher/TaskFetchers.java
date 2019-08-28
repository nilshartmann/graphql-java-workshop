package nh.graphql.tasks.graphql.fetcher;

import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import nh.graphql.tasks.domain.Task;
import nh.graphql.tasks.domain.user.User;
import nh.graphql.tasks.domain.user.UserService;

@Service
public class TaskFetchers {

  @Autowired
  private UserService userService;

  public DataFetcher<Object> assignee = new DataFetcher<>() {
    @Override
    public Object get(DataFetchingEnvironment environment) throws Exception {
      Task source = environment.getSource();
      String userId = source.getAssigneeId();
      boolean useDataLoader = environment.getField().getDirective("useDataLoader") != null;

      if (!useDataLoader) {
        return userService.getUser(userId).orElse(null);
      }

      DataLoader<String, User> dataLoader = environment.getDataLoader("userDataLoader");
      return dataLoader.load(userId);
    }
  };

}

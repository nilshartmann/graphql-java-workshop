package nh.graphql.projectmgmt.graphql.fetcher;

import org.dataloader.DataLoader;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import nh.graphql.projectmgmt.domain.Task;
import nh.graphql.projectmgmt.domain.user.User;
import nh.graphql.projectmgmt.domain.user.UserService;
import nh.graphql.projectmgmt.graphql.ProjectMgmtGraphQLContext;

public class TaskFetchers {

  public DataFetcher<Object> assignee = new DataFetcher<>() {
    @Override
    public Object get(DataFetchingEnvironment environment) throws Exception {
      Task source = environment.getSource();
      String userId = source.getAssigneeId();
      boolean useDataLoader = environment.getField().getDirective("useDataLoader") != null;

      if (!useDataLoader) {
        ProjectMgmtGraphQLContext context = environment.getContext();
        UserService userService = context.getUserService();
        return userService.getUser(userId).orElse(null);
      }

      DataLoader<String, User> dataLoader = environment.getDataLoader("userDataLoader");
      return dataLoader.load(userId);
    }
  };

}

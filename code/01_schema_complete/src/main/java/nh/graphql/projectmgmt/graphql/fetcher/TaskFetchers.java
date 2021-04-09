package nh.graphql.projectmgmt.graphql.fetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import nh.graphql.projectmgmt.domain.Task;
import nh.graphql.projectmgmt.domain.user.UserService;
import nh.graphql.projectmgmt.graphql.ProjectMgmtGraphQLContext;

public class TaskFetchers {

  public DataFetcher<Object> assignee = new DataFetcher<>() {
    @Override
    public Object get(DataFetchingEnvironment environment) throws Exception {
      Task source = environment.getSource();
      String userId = source.getAssigneeId();

      ProjectMgmtGraphQLContext context = environment.getContext();
      UserService userService = context.getUserService();
      return userService.getUser(userId).orElse(null);
    }
  };

}

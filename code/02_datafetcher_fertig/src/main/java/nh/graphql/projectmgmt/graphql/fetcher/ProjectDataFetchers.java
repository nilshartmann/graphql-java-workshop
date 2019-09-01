package nh.graphql.projectmgmt.graphql.fetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import nh.graphql.projectmgmt.domain.Project;
import nh.graphql.projectmgmt.domain.Task;
import nh.graphql.projectmgmt.graphql.ProjectMgmtGraphQLContext;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class ProjectDataFetchers {

  public DataFetcher<Task> task = new DataFetcher<>() {
    @Override
    public Task get(DataFetchingEnvironment environment) throws Exception {
      ProjectMgmtGraphQLContext context = environment.getContext();
      long id = Long.parseLong(environment.getArgument("id"));
      return context.getTaskRepository().findById(id).orElse(null);
    }
  };

  public DataFetcher<Object> owner = new DataFetcher<>() {
    @Override
    public Object get(DataFetchingEnvironment environment) throws Exception {
      Project project = environment.getSource();
      String userId = project.getOwnerId();

      ProjectMgmtGraphQLContext context = environment.getContext();
      return context.getUserService().getUser(userId).orElse(null);
    }
  };
}

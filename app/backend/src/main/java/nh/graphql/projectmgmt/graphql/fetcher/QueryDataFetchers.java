package nh.graphql.projectmgmt.graphql.fetcher;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import nh.graphql.projectmgmt.domain.Project;
import nh.graphql.projectmgmt.domain.user.User;
import nh.graphql.projectmgmt.graphql.ProjectMgmtGraphQLContext;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class QueryDataFetchers {

  public DataFetcher<Iterable<User>> users = new DataFetcher<>() {
    @Override
    public Iterable<User> get(DataFetchingEnvironment environment) {
      ProjectMgmtGraphQLContext context = environment.getContext();
      return context.getUserService().getAllUsers();
    }
  };

  public DataFetcher<Optional<User>> user = new DataFetcher<>() {
    @Override
    public Optional<User> get(DataFetchingEnvironment environment) {
      String userId = environment.getArgument("id");
      ProjectMgmtGraphQLContext context = environment.getContext();
      return context.getUserService().getUser(userId);
    }
  };

  public DataFetcher<Optional<Project>> projectById = new DataFetcher<>() {
    @Override
    public Optional<Project> get(DataFetchingEnvironment environment) {
      ProjectMgmtGraphQLContext context = environment.getContext();
      long id = Long.parseLong(environment.getArgument("id"));
      return context.getProjectRepository().findById(id, isCategorySelected(environment), isTasksSelected(environment));
    }
  };

  public DataFetcher<Iterable<Project>> projects = new DataFetcher<>() {
    @Override
    public Iterable<Project> get(DataFetchingEnvironment environment) throws Exception {
      ProjectMgmtGraphQLContext context = environment.getContext();
      return context.getProjectRepository().findAll(isCategorySelected(environment), isTasksSelected(environment));
    }
  };

  private boolean isCategorySelected(DataFetchingEnvironment environment) {
    boolean useEntityGraph = environment.getField().hasDirective("useEntityGraph");
    return useEntityGraph && environment.getSelectionSet().contains("category");
  }

  private boolean isTasksSelected(DataFetchingEnvironment environment) {
    boolean useEntityGraph = environment.getField().hasDirective("useEntityGraph");
    return useEntityGraph && environment.getSelectionSet().contains("tasks");
  }

}

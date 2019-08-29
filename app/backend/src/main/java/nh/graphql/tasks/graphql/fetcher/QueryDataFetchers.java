package nh.graphql.tasks.graphql.fetcher;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import nh.graphql.tasks.domain.Project;
import nh.graphql.tasks.domain.ProjectRepository;
import nh.graphql.tasks.domain.user.User;
import nh.graphql.tasks.domain.user.UserService;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class QueryDataFetchers {

  @Autowired
  private UserService userService;

  @Autowired
  private ProjectRepository projectRepository;

//  public DataFetcher<String> ping = env -> "Hello, World @ "
//      + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));

  public DataFetcher<String> ping = new DataFetcher<>() {
    @Override
    public String get(DataFetchingEnvironment environment) {
      String msg = environment.getArgument("msg");
      if (msg == null) {
        msg = "World";
      }
      return "Hello, " + msg + " @ " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
    }
  };

  public DataFetcher<Iterable<User>> users = new DataFetcher<>() {
    @Override
    public Iterable<User> get(DataFetchingEnvironment environment) {
      return userService.getAllUsers();
    }
  };

  public DataFetcher<Optional<User>> user = new DataFetcher<>() {
    @Override
    public Optional<User> get(DataFetchingEnvironment environment) {
      String userId = environment.getArgument("id");
      return userService.getUser(userId);
    }
  };

  public DataFetcher<Optional<Project>> projectById = new DataFetcher<>() {
    @Override
    public Optional<Project> get(DataFetchingEnvironment environment) {
      long id = Long.parseLong(environment.getArgument("id"));
      return projectRepository.findById(id, isCategorySelected(environment), isTasksSelected(environment));
    }
  };

  public DataFetcher<Iterable<Project>> projects = new DataFetcher<>() {
    @Override
    public Iterable<Project> get(DataFetchingEnvironment environment) throws Exception {
      return projectRepository.findAll(isCategorySelected(environment), isTasksSelected(environment));
    }
  };

  private boolean isCategorySelected(DataFetchingEnvironment environment) {
    boolean useEntityGraph = environment.getField().getDirective("useEntityGraph") != null;
    return useEntityGraph && environment.getSelectionSet().contains("category");
  }

  private boolean isTasksSelected(DataFetchingEnvironment environment) {
    boolean useEntityGraph = environment.getField().getDirective("useEntityGraph") != null;
    return useEntityGraph && environment.getSelectionSet().contains("tasks");
  }

}

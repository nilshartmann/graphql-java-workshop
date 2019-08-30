package nh.graphql.projectmgmt.graphql.fetcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import nh.graphql.projectmgmt.domain.Project;
import nh.graphql.projectmgmt.domain.Task;
import nh.graphql.projectmgmt.domain.TaskRepository;
import nh.graphql.projectmgmt.domain.user.UserService;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class ProjectDataFetchers {
  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private UserService userService;

  public DataFetcher<Task> task = new DataFetcher<>() {
    @Override
    public Task get(DataFetchingEnvironment environment) throws Exception {
      long id = Long.parseLong(environment.getArgument("id"));
      return taskRepository.findById(id).orElse(null);
    }
  };

  public DataFetcher<Object> owner = new DataFetcher<>() {
    @Override
    public Object get(DataFetchingEnvironment environment) throws Exception {
      Project project = environment.getSource();
      String userId = project.getOwnerId();
      return userService.getUser(userId).orElse(null);
    }
  };
}

package nh.graphql.tasks.graphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import nh.graphql.tasks.domain.Project;
import nh.graphql.tasks.domain.Task;
import nh.graphql.tasks.domain.TaskRepository;
import nh.graphql.tasks.domain.user.User;
import nh.graphql.tasks.domain.user.UserService;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class ProjectDataFetchers {

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private UserService userService;

  DataFetcher<Task> task = new DataFetcher<>() {
    @Override
    public Task get(DataFetchingEnvironment environment) throws Exception {
      long id = Long.parseLong(environment.getArgument("id"));
      return taskRepository.findById(id).orElse(null);
    }
  };

  DataFetcher<User> owner = new DataFetcher<>() {
    @Override
    public User get(DataFetchingEnvironment environment) throws Exception {
      Project project = environment.getSource();
      return userService.getUser(project.getOwnerId()).orElse(null);
    }
  };

}

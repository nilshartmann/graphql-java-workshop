package nh.graphql.projectmgmt.graphql.fetcher;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import graphql.schema.DataFetchingEnvironment;
import nh.graphql.projectmgmt.domain.Project;
import nh.graphql.projectmgmt.domain.ProjectRepository;
import nh.graphql.projectmgmt.domain.user.User;
import nh.graphql.projectmgmt.domain.user.UserService;

@Component
public class QueryResolver implements GraphQLQueryResolver {

  @Autowired
  private UserService userService;

  @Autowired
  private ProjectRepository projectRepository;

  public String ping(String msg) {
    if (msg == null) {
      msg = "World";
    }
    return "Hello, " + msg + " @ " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
  }

  public Iterable<User> users() {
    return userService.getAllUsers();
  }

  public Optional<User> user(String userId) {
    return userService.getUser(userId);
  }

  public Optional<Project> project(long projectId, DataFetchingEnvironment environment) {
    return projectRepository.findById(projectId, isCategorySelected(environment), isTasksSelected(environment));
  }

  public Iterable<Project> projects(DataFetchingEnvironment environment) {
    return projectRepository.findAll(isCategorySelected(environment), isTasksSelected(environment));
  }

  private boolean isCategorySelected(DataFetchingEnvironment environment) {
    return environment.getSelectionSet().contains("category");
  }

  private boolean isTasksSelected(DataFetchingEnvironment environment) {
    return environment.getSelectionSet().contains("tasks");
  }
}

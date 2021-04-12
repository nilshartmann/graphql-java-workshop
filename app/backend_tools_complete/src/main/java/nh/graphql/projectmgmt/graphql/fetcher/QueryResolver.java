package nh.graphql.projectmgmt.graphql.fetcher;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.schema.DataFetchingEnvironment;
import nh.graphql.projectmgmt.domain.Project;
import nh.graphql.projectmgmt.domain.ProjectRepository;
import nh.graphql.projectmgmt.domain.user.User;
import nh.graphql.projectmgmt.domain.user.UserService;

@Component
public class QueryResolver implements GraphQLQueryResolver {

  private static final Logger logger = LoggerFactory.getLogger(QueryResolver.class);

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

  public List<Project> projects(DataFetchingEnvironment environment) {
    return projectRepository.findAll(isCategorySelected(environment), isTasksSelected(environment));
  }

//  public ProjectConnection projects(Optional<Integer> pageArgument, Optional<Integer> pageSizeArgument,
//      DataFetchingEnvironment environment) {
//
//    int page = pageArgument.orElse(0);
//    int pageSize = pageSizeArgument.orElse(5);
//
//    logger.info("pageArgument {}, page {}, pageSizeArgument {}, pageSize {}", pageArgument, page, pageSizeArgument,
//        pageSize);
//
//    // This is stupid and would be optimized in a 'real' application, so that limit
//    // and offset are already
//    // used in the SQL query
//    List<Project> result = projectRepository.findAll(isCategorySelected(environment), isTasksSelected(environment));
//
//    return ProjectConnection.fromList(result, page, pageSize);
//  }

  private boolean isCategorySelected(DataFetchingEnvironment environment) {
    return environment.getSelectionSet().contains("category");
  }

  private boolean isTasksSelected(DataFetchingEnvironment environment) {
    return environment.getSelectionSet().contains("tasks");
  }
}

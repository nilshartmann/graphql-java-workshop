package nh.graphql.projectmgmt.graphql.fetcher;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import nh.graphql.projectmgmt.domain.Project;
import nh.graphql.projectmgmt.domain.Task;
import nh.graphql.projectmgmt.domain.TaskRepository;
import nh.graphql.projectmgmt.domain.user.User;
import nh.graphql.projectmgmt.domain.user.UserService;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class ProjectDataFetchers {
  private static final Logger logger = LoggerFactory.getLogger(ProjectDataFetchers.class);
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
      boolean useDataLoader = environment.getField().getDirective("useDataLoader") != null;

      if (!useDataLoader) {
        return userService.getUser(userId).orElse(null);
      }

      DataLoader<String, User> dataLoader = environment.getDataLoader("userDataLoader");
      return dataLoader.load(userId);
    }
  };

  public BatchLoader<String, Optional<User>> userBatchLoader = new BatchLoader<>() {
    @Override
    public CompletionStage<List<Optional<User>>> load(List<String> keys) {
      logger.info("UserBatchLoader - loading Users with keys '{}'", keys);
      return CompletableFuture.supplyAsync(() -> {
        return keys.stream() //
            .map(userService::getUser) //
            .collect(Collectors.toList());
      });
    }
  };

  // graphql.schema.AsyncDataFetcher.async
//  public DataFetcher estimation = (env -> {
//    logger.info("Thread {} ", Thread.currentThread().getName());
//    Thread.sleep(500);
//    return "Thread " + Thread.currentThread().getName() + " finished @ "
//        + LocalDateTime.now().format(DateTimeFormatter.ofPattern("mm:ss.SSS"));
//  });

}

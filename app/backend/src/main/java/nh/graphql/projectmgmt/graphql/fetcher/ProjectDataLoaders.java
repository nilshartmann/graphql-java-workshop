package nh.graphql.projectmgmt.graphql.fetcher;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import org.dataloader.BatchLoaderEnvironment;
import org.dataloader.BatchLoaderWithContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nh.graphql.projectmgmt.domain.user.User;
import nh.graphql.projectmgmt.domain.user.UserService;
import nh.graphql.projectmgmt.graphql.ProjectMgmtGraphQLContext;

public class ProjectDataLoaders {

  private static final Logger logger = LoggerFactory.getLogger(ProjectDataLoaders.class);

  public BatchLoaderWithContext<String, Optional<User>> userBatchLoader = new BatchLoaderWithContext<>() {
    @Override
    public CompletableFuture<List<Optional<User>>> load(List<String> keys, BatchLoaderEnvironment environment) {
      logger.info("UserBatchLoader - loading Users with keys '{}'", keys);
      ProjectMgmtGraphQLContext context = environment.getContext();
      final UserService userService = context.getUserService();
      return CompletableFuture.supplyAsync(() -> {
        return keys.stream() //
            .map(userService::getUser) //
            .collect(Collectors.toList());
      });
    }
  };
}

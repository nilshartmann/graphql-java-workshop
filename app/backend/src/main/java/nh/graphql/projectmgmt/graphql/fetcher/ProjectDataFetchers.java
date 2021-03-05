package nh.graphql.projectmgmt.graphql.fetcher;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.dataloader.DataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import nh.graphql.projectmgmt.domain.Project;
import nh.graphql.projectmgmt.domain.Task;
import nh.graphql.projectmgmt.domain.user.User;
import nh.graphql.projectmgmt.graphql.ProjectMgmtGraphQLContext;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class ProjectDataFetchers {
  private static final Logger logger = LoggerFactory.getLogger(ProjectDataFetchers.class);

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
      boolean useDataLoader = environment.getField().hasDirective("useDataLoader");

      if (!useDataLoader) {
        ProjectMgmtGraphQLContext context = environment.getContext();
        return context.getUserService().getUser(userId).orElse(null);
      }

      DataLoader<String, User> dataLoader = environment.getDataLoader("userDataLoader");
      return dataLoader.load(userId);
    }
  };




  @SuppressWarnings("rawtypes")
//  public DataFetcher estimation = graphql.schema.AsyncDataFetcher.async(env -> {
  public DataFetcher estimation = (env -> {
    logger.info("Thread {} ", Thread.currentThread().getName());
    Thread.sleep(500);
    return "Thread " + Thread.currentThread().getName() + " finished @ "
        + LocalDateTime.now().format(DateTimeFormatter.ofPattern("mm:ss.SSS"));
  });

}

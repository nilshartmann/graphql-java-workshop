package nh.graphql.projectmgmt.graphql.fetcher;

import org.reactivestreams.Publisher;

import graphql.schema.DataFetcher;
import nh.graphql.projectmgmt.domain.Task;
import nh.graphql.projectmgmt.graphql.config.ProjectMgmtGraphQLContext;

public class SubscriptionFetchers {

  public DataFetcher<Publisher<Task>> onNewTask = dfe -> {
    ProjectMgmtGraphQLContext context = dfe.getContext();
    return context.getTaskPublisher().getPublisher();
  };

}

package nh.graphql.tasks.graphql.fetcher;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.schema.DataFetcher;
import nh.graphql.tasks.domain.Task;
import nh.graphql.tasks.domain.TaskPublisher;

@Component
public class SubscriptionFetchers {

  @Autowired
  private TaskPublisher taskPublisher;

  public DataFetcher<Publisher<Task>> onNewTask = dfe -> taskPublisher.getPublisher();

}

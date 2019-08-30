package nh.graphql.projectmgmt.graphql.fetcher;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.schema.DataFetcher;
import nh.graphql.projectmgmt.domain.Task;
import nh.graphql.projectmgmt.domain.TaskPublisher;

@Component
public class SubscriptionFetchers {

  @Autowired
  private TaskPublisher taskPublisher;

  public DataFetcher<Publisher<Task>> onNewTask = dfe -> taskPublisher.getPublisher();

}

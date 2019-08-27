package nh.graphql.tasks.graphql;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.schema.DataFetcher;
import nh.graphql.tasks.domain.Task;
import nh.graphql.tasks.domain.TaskPublisher;

@Component
public class SubscriptionFetchers {

  private static final Logger logger = LoggerFactory.getLogger(SubscriptionFetchers.class);

  @Autowired
  private TaskPublisher taskPublisher;

  DataFetcher<Publisher<Task>> onNewTask = dfe -> {
    logger.info("Subscription request for onNewTask received");
    return taskPublisher.getPublisher();
  };

}

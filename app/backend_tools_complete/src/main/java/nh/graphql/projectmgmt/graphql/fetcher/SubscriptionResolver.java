package nh.graphql.projectmgmt.graphql.fetcher;

import graphql.kickstart.tools.GraphQLSubscriptionResolver;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import graphql.schema.DataFetchingEnvironment;
import nh.graphql.projectmgmt.domain.Task;
import nh.graphql.projectmgmt.domain.TaskPublisher;

@Component
public class SubscriptionResolver implements GraphQLSubscriptionResolver {

  @Autowired
  private TaskPublisher taskPublisher;

  public Publisher<Task> onNewTask(DataFetchingEnvironment environment) {
    return taskPublisher.getPublisher();
  };

}

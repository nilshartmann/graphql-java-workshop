package nh.graphql.projectmgmt.graphql.fetcher;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLSubscriptionResolver;

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

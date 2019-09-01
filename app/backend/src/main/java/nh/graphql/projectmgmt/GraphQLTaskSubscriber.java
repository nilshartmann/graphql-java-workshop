package nh.graphql.projectmgmt;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.ExecutionResult;
import graphql.GraphQL;

@Component
public class GraphQLTaskSubscriber {

  private static final Logger logger = LoggerFactory.getLogger(GraphQLTaskSubscriber.class);

  @Autowired
  private GraphQL graphQL;

  public void executeAndSubscribe() {
    ExecutionResult execute = graphQL.execute("subscription {onNewTask {id title}}");
    Publisher<ExecutionResult> stream = execute.getData();
    logger.info("New Stream created {}", stream);
    stream.subscribe(new Subscriber<ExecutionResult>() {
      private Subscription subscription;

      @Override
      public void onSubscribe(Subscription s) {
        s.request(1);
        subscription = s;

      }

      @Override
      public void onNext(ExecutionResult t) {
        try {
          Object data = t.getData();
          logger.info("Received new Task {}", data);
          subscription.request(1);
        } catch (Exception ex) {
          logger.error("Error in onNext", ex);
        }
      }

      @Override
      public void onError(Throwable t) {
        logger.error("Error in Listener {}", t);
      }

      @Override
      public void onComplete() {
        logger.info("onComplete");
      }
    });
  }

}

package nh.graphql.projectmgmt;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nh.graphql.projectmgmt.domain.Task;
import nh.graphql.projectmgmt.domain.TaskPublisher;

@Component
public class RxStreamsTaskSubscriber {

  private static final Logger logger = LoggerFactory.getLogger(RxStreamsTaskSubscriber.class);

  @Autowired
  private TaskPublisher taskPublisher;

  public void subscribe() {
    taskPublisher.getPublisher().subscribe(new Subscriber<Task>() {
      private Subscription subscription;

      @Override
      public void onSubscribe(Subscription s) {
        s.request(1);
        subscription = s;

      }

      @Override
      public void onNext(Task t) {
        logger.info("Received new Task {}", t);
        subscription.request(1);
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

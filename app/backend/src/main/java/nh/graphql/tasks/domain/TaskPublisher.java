package nh.graphql.tasks.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.observables.ConnectableObservable;

@Component
public class TaskPublisher {

  private static final Logger logger = LoggerFactory.getLogger(TaskPublisher.class);

  private final Flowable<Task> publisher;

  private ObservableEmitter<Task> emitter;

  public TaskPublisher() {
    Observable<Task> newTaskObservable = Observable.create(emitter -> {
      this.emitter = emitter;
    });

    ConnectableObservable<Task> connectableObservable = newTaskObservable.share().publish();
    connectableObservable.connect();

    publisher = connectableObservable.toFlowable(BackpressureStrategy.BUFFER);
  }

  public void publishNewTask(final Task task) {
    logger.info("Publishing new Task {}", task);
    emitter.onNext(task);
  }

  public Flowable<Task> getPublisher() {
    return this.publisher;
  }
}

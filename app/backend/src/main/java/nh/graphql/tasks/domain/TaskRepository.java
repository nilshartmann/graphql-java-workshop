package nh.graphql.tasks.domain;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Repository
public class TaskRepository {

  @PersistenceContext
  private EntityManager em;

  public Task save(Task task) {
    em.persist(task);
    return task;
  }

  public Optional<Task> findById(long id) {
    Task task = em.find(Task.class, id);
    return Optional.ofNullable(task);
  }
}

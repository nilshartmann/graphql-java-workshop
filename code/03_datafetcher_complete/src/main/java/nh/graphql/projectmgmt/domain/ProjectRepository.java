package nh.graphql.projectmgmt.domain;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Repository
public class ProjectRepository {

  @PersistenceContext
  private EntityManager em;

  public Project save(Project project) {
    em.persist(project);
    return project;
  }

  public Optional<Project> findById(long id) {
    Project project = em.find(Project.class, id);
    return Optional.ofNullable(project);
  }

  public Iterable<Project> findAll() {
    TypedQuery<Project> query = em.createQuery("SELECT p FROM Project p ORDER BY p.id", Project.class);
    return query.getResultList();
  }
}

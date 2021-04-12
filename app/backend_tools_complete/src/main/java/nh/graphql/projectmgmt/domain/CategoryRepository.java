package nh.graphql.projectmgmt.domain;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */

@Repository
public class CategoryRepository {
  @PersistenceContext
  private EntityManager em;

  public Category save(Category category) {
    em.persist(category);
    return category;
  }
}

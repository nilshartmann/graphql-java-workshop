package nh.graphql.projectmgmt.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Repository
public class ProjectRepository {

  private static final Logger logger = LoggerFactory.getLogger(ProjectRepository.class);

  @PersistenceContext
  private EntityManager em;

  public Project save(Project project) {
    em.persist(project);
    return project;
  }

  public Optional<Project> findById(long id, boolean withCategory, boolean withTasks) {
    logger.info("FIND PROJECT BY ID, withCategory: {}, withTasks: {}", withCategory, withTasks);
    EntityGraph<Project> entityGraph = buildFetchHints(withCategory, withTasks);

    Map<String, Object> hints = null;
    if (entityGraph != null) {
      hints = new HashMap<>();
      hints.put("javax.persistence.fetchgraph", entityGraph);
    }

    Project project = em.find(Project.class, id, hints);
    return Optional.ofNullable(project);
  }

  public List<Project> findAll(boolean withCategory, boolean withTasks) {
    logger.info("FIND ALL PROJECTS, withCategory: {}, withTasks: {}", withCategory, withTasks);

    EntityGraph<Project> entityGraph = buildFetchHints(withCategory, withTasks);
    TypedQuery<Project> query = em.createQuery("SELECT p FROM Project p ORDER BY p.id", Project.class);
    if (entityGraph != null) {
      query.setHint("javax.persistence.fetchgraph", entityGraph);
    }
    return query.getResultList();
  }

  private EntityGraph<Project> buildFetchHints(boolean withCategory, boolean withTasks) {
    logger.trace("withCategory {}, withTasks {}", withCategory, withTasks);
    if (!withCategory && !withTasks) {
      return null;
    }
    EntityGraph<Project> entityGraph = em.createEntityGraph(Project.class);
    if (withCategory) {
      entityGraph.addSubgraph("category");
    }
    if (withTasks) {
      entityGraph.addSubgraph("tasks");
    }

    return entityGraph;

  }

}

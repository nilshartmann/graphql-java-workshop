package nh.graphql.tasks.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public interface ProjectRepository extends CrudRepository<Project, Long> {

}

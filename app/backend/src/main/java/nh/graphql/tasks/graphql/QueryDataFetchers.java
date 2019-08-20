package nh.graphql.tasks.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import nh.graphql.tasks.domain.Project;
import nh.graphql.tasks.domain.ProjectRepository;
import nh.graphql.tasks.domain.user.User;
import nh.graphql.tasks.domain.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class QueryDataFetchers {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectRepository projectRepository;

    DataFetcher<Iterable<User>> users = new DataFetcher<>() {
        @Override
        public Iterable<User> get(DataFetchingEnvironment environment)  {
            return userService.getAllUsers();
        }
    };

    DataFetcher<Optional<Project>> project = new DataFetcher<>() {
        @Override
        public Optional<Project> get(DataFetchingEnvironment environment)  {
            long id = Long.parseLong(environment.getArgument("id"));
            return projectRepository.findById(id);
        }
    };

    DataFetcher<Iterable<Project>> projects = new DataFetcher<>() {
        @Override
        public Iterable<Project> get(DataFetchingEnvironment environment) throws Exception {
            return projectRepository.findAll();
        }
    };

}

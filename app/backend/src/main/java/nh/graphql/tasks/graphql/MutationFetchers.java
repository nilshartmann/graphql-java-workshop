package nh.graphql.tasks.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import nh.graphql.tasks.domain.Project;
import nh.graphql.tasks.domain.ProjectRepository;
import nh.graphql.tasks.domain.Task;
import nh.graphql.tasks.domain.user.User;
import nh.graphql.tasks.domain.user.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class MutationFetchers {
    private final static Logger logger = LoggerFactory.getLogger(MutationFetchers.class);

    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private UserService userService;

    DataFetcher changeProjectTitle = new DataFetcher() {
        @Override
        public Object get(DataFetchingEnvironment environment) throws Exception {
            String projectId = environment.getArgument("id");
            String newTitle = environment.getArgument("newTitle");

            logger.info("Set title to '{}' for project '{}'", newTitle, projectId);

            Project p = projectRepository.findById(Long.parseLong(projectId)).orElse(null);
            if (p == null) {
                logger.warn("Project not found");
                return null;
            }

            p.setTitle(newTitle);
            projectRepository.save(p);

            return p;
        }
    };

    DataFetcher addTask = new DataFetcher() {
        @Override
        public Object get(DataFetchingEnvironment environment) throws Exception {
            long projectId = Long.parseLong(environment.getArgument("projectId"));
            Map<String, String> input = environment.getArgument("input");

            String title = input.get("title");
            String description = input.get("description");
            DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS[xxx][xx][X]");
            LocalDateTime toBeFinishedAt = LocalDateTime.parse(input.get("toBeFinishedAt"), formatter);
            String assigneeId = input.get("assigneeId");

            Project project = projectRepository.findById(projectId).orElseThrow();
            
            // check userService to make sure user with given assigneeId exists
            userService.getUser(assigneeId).orElseThrow();

            Task task = new Task(assigneeId, title, description, toBeFinishedAt);
            project.addTask(task);

            projectRepository.save(project);

            return task;
        }
    };
}

package nh.graphql.tasks.graphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import nh.graphql.tasks.domain.Task;
import nh.graphql.tasks.domain.user.User;
import nh.graphql.tasks.domain.user.UserService;

@Service
public class TaskFetchers {
  
  @Autowired
  private UserService userService;
  
  DataFetcher<User> assignee = new DataFetcher<>() {
    @Override
    public User get(DataFetchingEnvironment environment) throws Exception {
      Task source = environment.getSource();
      return userService.getUser(source.getAssigneeId()).orElse(null);
    }
  };

}

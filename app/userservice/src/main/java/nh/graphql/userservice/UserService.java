package nh.graphql.userservice;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@RestController
public class UserService {

  private Logger logger = LoggerFactory.getLogger(UserService.class);

  private final List<User> allUsers = new LinkedList<>();

  // for debugging
  private AtomicInteger usersRequestCounter = new AtomicInteger();
  private AtomicInteger usersByIdCounter = new AtomicInteger();

  @GetMapping("/users")
  public Object getUsers() {
    int usersRequestCounter = this.usersRequestCounter.incrementAndGet();
    logger.info("READING ALL USERS (REQUEST-ID: {}})", usersRequestCounter);

    return allUsers.stream().map(user -> mapUserToDtoWithRequestId(user, "users_" + usersRequestCounter))
        .collect(Collectors.toList());
  }

  @GetMapping("/users/{userId}")
  public Object getUserById(@PathVariable String userId) {
    int usersByIdCounter = this.usersByIdCounter.incrementAndGet();
    logger.info("READING USER WITH ID '{}'' (REQUEST-ID: {}})", userId, usersByIdCounter);

    return allUsers.stream().filter(u -> userId.equals(u.getId()))
        .map(user -> mapUserToDtoWithRequestId(user, "users_" + usersByIdCounter)).findFirst().orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User '%s' not found", userId)));
  }

  private Map<String, String> mapUserToDtoWithRequestId(User user, String requestId) {
    Map<String, String> result = new Hashtable<>();

    result.put("id", user.getId());
    result.put("name", user.getName());
    result.put("login", user.getLogin());
    result.put("requestId", requestId);

    return result;

  }

  @PostConstruct
  public void initUsers() {
    allUsers.add(new User("U1", "nils", "Nils Hartmann"));
    allUsers.add(new User("U2", "susi", "Susi Mueller"));
    allUsers.add(new User("U3", "klaus", "Klaus Schneider"));
    allUsers.add(new User("U4", "sue", "Sue Taylor"));
    allUsers.add(new User("U5", "lauren", "Lauren Jones"));
    allUsers.add(new User("U6", "olivia", "Olivia Smith"));
    allUsers.add(new User("U7", "cathy", "Cath Brown"));
    allUsers.add(new User("U8", "maja", "Maja Walsh"));
  }

}

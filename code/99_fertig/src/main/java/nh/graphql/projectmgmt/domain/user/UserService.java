package nh.graphql.projectmgmt.domain.user;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  private final RestTemplate restTemplate;
  private final String userServiceUrl;

  public UserService(RestTemplate restTemplate,
      @Value("${userservice.url:http://localhost:5010/users}") String userServiceUrl) {
    this.restTemplate = restTemplate;
    this.userServiceUrl = userServiceUrl;

    logger.info("USING USERSERVICE API URL {}", this.userServiceUrl);

  }

  public Optional<User> getUser(String userId) {
    logger.info("READING USER WITH ID '{}' FROM '{}'", userId, this.userServiceUrl);
    User user = null;
    try {
      user = restTemplate.getForObject(this.userServiceUrl + "/{userId}", User.class, userId);
    } catch (HttpClientErrorException ex) {
      if (ex.getStatusCode() != HttpStatus.NOT_FOUND) {
        throw ex;
      }
    }

    return Optional.ofNullable(user);
  }

  public List<User> getAllUsers() {
    logger.info("READING ALL USERS FROM '{}'", this.userServiceUrl);
    ResponseEntity<List<User>> response = restTemplate.exchange(this.userServiceUrl, HttpMethod.GET, null,
        new ParameterizedTypeReference<List<User>>() {
        });
    List<User> users = response.getBody();
    return users;
  }
}

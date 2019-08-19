package nh.graphql.userservice;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class User {

  private final String id;
  private final String login;
  private final String name;

  public User(String id, String login, String name) {
    this.id = id;
    this.name = name;
    this.login = login;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getLogin() {
    return login;
  }

  @Override
  public String toString() {
    return "User{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", login='" + login + '\'' + '}';
  }
}

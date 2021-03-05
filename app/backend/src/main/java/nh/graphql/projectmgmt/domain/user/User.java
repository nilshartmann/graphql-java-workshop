package nh.graphql.projectmgmt.domain.user;

public class User {
  private String id;
  private String login;
  private String name;
  private String requestId;

  public User() {
  }

  public User(String id, String login, String name) {
    this.id = id;
    this.login = login;
    this.name = name;
    this.requestId = "";
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", login=" + login + ", name=" + name + ", requestId=" + requestId + "]";
  }

}

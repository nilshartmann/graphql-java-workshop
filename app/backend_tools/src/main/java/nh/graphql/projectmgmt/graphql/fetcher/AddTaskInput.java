package nh.graphql.projectmgmt.graphql.fetcher;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddTaskInput {

  private final static DateTimeFormatter formatter = DateTimeFormatter
      .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS[xxx][xx][X]");

  private String title;
  private String description;
  private String toBeFinishedAt;
  private String assigneeId;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getToBeFinishedAt() {
    return toBeFinishedAt;
  }

  public void setToBeFinishedAt(String toBeFinishedAt) {
    this.toBeFinishedAt = toBeFinishedAt;
  }

  public String getAssigneeId() {
    return assigneeId;
  }

  public void setAssigneeId(String assigneeId) {
    this.assigneeId = assigneeId;
  }

  public LocalDateTime getToBeFinishedAtDate() {
    LocalDateTime toBeFinishedAt = this.toBeFinishedAt != null && this.toBeFinishedAt.length() > 0
        ? LocalDateTime.parse(this.toBeFinishedAt, formatter)
        : LocalDateTime.now().plusDays(14);

    return toBeFinishedAt;
  }

}

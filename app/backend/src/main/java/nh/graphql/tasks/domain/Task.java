package nh.graphql.tasks.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Entity
@Table(name = "tasks")
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_id_generator")
  @SequenceGenerator(name = "task_id_generator", sequenceName = "task_id_seq", initialValue = 2000)
  @Column(name = "id", updatable = false, nullable = false)
  private long id;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "project_id")
//    private Project project;

  @NotNull
  private String assigneeId;

  @NotNull
  @Column(name = "title", nullable = false)
  private String title;

  @NotNull
  @Column(name = "description", columnDefinition = "TEXT", nullable = false)
  private String description;

  @NotNull
  @Column(name = "state", nullable = false)
  private TaskState state;

  @NotNull
  @Column(name = "finish_date")
  private LocalDateTime toBeFinishedAt;

  protected Task() {
  }

  public Task(String assigneeId, String title, String description, LocalDateTime toBeFinishedAt) {
    this(assigneeId, title, description, TaskState.NEW, toBeFinishedAt);
  }

  /**
   * FOR TEST/IMPORT ONLY
   */
  public Task(String assigneeId, String title, String description, TaskState state, LocalDateTime toBeFinishedAt) {
    this.assigneeId = assigneeId;
    this.toBeFinishedAt = toBeFinishedAt;
    this.title = title;
    this.description = description;
    this.state = state;
  }

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

  public TaskState getState() {
    return state;
  }

  public void setState(TaskState state) {
    this.state = state;
  }

  public long getId() {
    return id;
  }

  public LocalDateTime getToBeFinishedAt() {
    return toBeFinishedAt;
  }

  public void setToBeFinishedAt(LocalDateTime toBeFinishedAt) {
    this.toBeFinishedAt = toBeFinishedAt;
  }

  public String getAssigneeId() {
    return assigneeId;
  }

  @Override
  public String toString() {
    return "Task [id=" + id + ", assigneeId=" + assigneeId + ", title=" + title + ", description=" + description
        + ", state=" + state + ", toBeFinishedAt=" + toBeFinishedAt + "]";
  }

}

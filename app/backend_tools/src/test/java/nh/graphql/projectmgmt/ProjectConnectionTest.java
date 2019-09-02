package nh.graphql.projectmgmt;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import nh.graphql.projectmgmt.domain.Category;
import nh.graphql.projectmgmt.domain.Project;
import nh.graphql.projectmgmt.graphql.fetcher.ProjectConnection;

public class ProjectConnectionTest {

  @Test
  public void a() {
    List<Project> projects = someProjects(10);

    ProjectConnection connection = ProjectConnection.fromList(projects, 0, 10);
    assertThat(connection.getNodes().size()).isEqualTo(10);
    assertThat(connection.getPage().getPage()).isEqualTo(0);

    assertThat(connection.getPage().getTotalCount()).isEqualTo(10);
    assertThat(connection.getPage().isHasNextPage()).isFalse();
    assertThat(connection.getPage().isHasPreviousPage()).isFalse();
    assertThat(connection.getPage().getTotalPageCount()).isEqualTo(1);

    connection = ProjectConnection.fromList(projects, 0, 11);
    assertThat(connection.getNodes().size()).isEqualTo(10);
    assertThat(connection.getPage().getPage()).isEqualTo(0);

    assertThat(connection.getPage().getTotalCount()).isEqualTo(10);
    assertThat(connection.getPage().isHasNextPage()).isFalse();
    assertThat(connection.getPage().isHasPreviousPage()).isFalse();
    assertThat(connection.getPage().getTotalPageCount()).isEqualTo(1);

    connection = ProjectConnection.fromList(projects, 0, 9);
    assertThat(connection.getNodes().size()).isEqualTo(9);
    assertThat(connection.getPage().getPage()).isEqualTo(0);

    assertThat(connection.getPage().getTotalCount()).isEqualTo(10);
    assertThat(connection.getPage().isHasNextPage()).isTrue();
    assertThat(connection.getPage().isHasPreviousPage()).isFalse();
    assertThat(connection.getPage().getTotalPageCount()).isEqualTo(2);

  }

  @Test
  public void b() {
    List<Project> projects = someProjects(20);

    ProjectConnection connection = ProjectConnection.fromList(projects, 0, 10);
    assertThat(connection.getNodes().size()).isEqualTo(10);
    assertThat(connection.getNodes().get(0).getTitle()).isEqualTo("P1");
    assertThat(connection.getNodes().get(9).getTitle()).isEqualTo("P10");
    assertThat(connection.getPage().getPage()).isEqualTo(0);

    assertThat(connection.getPage().getTotalCount()).isEqualTo(20);
    assertThat(connection.getPage().isHasNextPage()).isTrue();
    assertThat(connection.getPage().isHasPreviousPage()).isFalse();
    assertThat(connection.getPage().getTotalPageCount()).isEqualTo(2);

  }

  @Test
  public void c() {
    List<Project> projects = someProjects(5);

    ProjectConnection connection = ProjectConnection.fromList(projects, 0, 2);
    assertThat(connection.getNodes().size()).isEqualTo(2);
    assertThat(connection.getNodes().get(0).getTitle()).isEqualTo("P1");
    assertThat(connection.getNodes().get(1).getTitle()).isEqualTo("P2");
    assertThat(connection.getPage().getPage()).isEqualTo(0);
    assertThat(connection.getPage().getTotalCount()).isEqualTo(5);
    assertThat(connection.getPage().isHasNextPage()).isTrue();
    assertThat(connection.getPage().isHasPreviousPage()).isFalse();
    assertThat(connection.getPage().getTotalPageCount()).isEqualTo(3);

    connection = ProjectConnection.fromList(projects, 1, 2);
    assertThat(connection.getNodes().size()).isEqualTo(2);
    assertThat(connection.getNodes().get(0).getTitle()).isEqualTo("P3");
    assertThat(connection.getNodes().get(1).getTitle()).isEqualTo("P4");
    assertThat(connection.getPage().getPage()).isEqualTo(1);
    assertThat(connection.getPage().getTotalCount()).isEqualTo(5);
    assertThat(connection.getPage().isHasNextPage()).isTrue();
    assertThat(connection.getPage().isHasPreviousPage()).isTrue();
    assertThat(connection.getPage().getTotalPageCount()).isEqualTo(3);

    connection = ProjectConnection.fromList(projects, 2, 2);
    assertThat(connection.getNodes().size()).isEqualTo(1);
    assertThat(connection.getNodes().get(0).getTitle()).isEqualTo("P5");
    assertThat(connection.getPage().getPage()).isEqualTo(2);
    assertThat(connection.getPage().getTotalCount()).isEqualTo(5);
    assertThat(connection.getPage().isHasNextPage()).isFalse();
    assertThat(connection.getPage().isHasPreviousPage()).isTrue();
    assertThat(connection.getPage().getTotalPageCount()).isEqualTo(3);

    connection = ProjectConnection.fromList(projects, 3, 2);
    assertThat(connection.getNodes().size()).isEqualTo(0);
    assertThat(connection.getPage().getPage()).isEqualTo(3);
    assertThat(connection.getPage().getTotalCount()).isEqualTo(5);
    assertThat(connection.getPage().isHasNextPage()).isFalse();
    assertThat(connection.getPage().isHasPreviousPage()).isTrue();
    assertThat(connection.getPage().getTotalPageCount()).isEqualTo(3);

  }

  private List<Project> someProjects(int howMany) {
    List<Project> projects = new LinkedList<>();
    for (int i = 1; i <= howMany; i++) {
      Project p = new Project("U1", new Category("Private"), "P" + i, "Project Description " + i);
      projects.add(p);
    }

    return projects;
  }

}

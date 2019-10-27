package nh.graphql.projectmgmt;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Nach der Übung 2 sollten diese Testfälle funktioneren. Um sie auszuführen,
 * bitte die @Ignore-Annotation unten entfernen
 * 
 * @author nils
 *
 */
@Ignore("Query funktioniert in dieser ausbaustufe noch nicht")
public class AllProjectMgmtApplicationTests extends ProjectMgmtApplicationTests {

  @Test
  public void projects() throws JsonProcessingException {
    GraphQLTestResponse graphQLTestResponse = execute("query {projects{id}}");
    assertThat(graphQLTestResponse.getInt("$.data.projects.length()")).isEqualTo(6);
  }

  @Test
  public void projectTasks() throws JsonProcessingException {
    GraphQLTestResponse graphQLTestResponse = execute("query {project(id:1){id tasks { title }}}");
    assertThat(graphQLTestResponse.getInt("$.data.project.tasks.length()")).isGreaterThanOrEqualTo(3);
  }

  @Test
  public void projectsPageQuery() {
    String query = loadGraphQL("ProjectsPageQuery");
    GraphQLTestResponse graphQLTestResponse = execute(query);
    assertThat(graphQLTestResponse.getInt("$.data.projects.length()")).isEqualTo(6);
  }

  @Test
  public void allUsersQuery() {
    String query = loadGraphQL("AllUsersQuery");
    GraphQLTestResponse graphQLTestResponse = execute(query);
    assertThat(graphQLTestResponse.getInt("$.data.users.length()")).isEqualTo(8);
  }

}

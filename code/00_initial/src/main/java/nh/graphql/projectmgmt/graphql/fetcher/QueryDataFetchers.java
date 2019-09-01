package nh.graphql.projectmgmt.graphql.fetcher;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import nh.graphql.projectmgmt.domain.user.User;
import nh.graphql.projectmgmt.graphql.ProjectMgmtGraphQLContext;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class QueryDataFetchers {

  public DataFetcher<String> ping = new DataFetcher<>() {
    @Override
    public String get(DataFetchingEnvironment environment) {
      String msg = environment.getArgument("msg");
      if (msg == null) {
        msg = "World";
      }
      return "Hello, " + msg + " @ " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
    }
  };

  public DataFetcher<Iterable<User>> users = new DataFetcher<>() {
    @Override
    public Iterable<User> get(DataFetchingEnvironment environment) {
      ProjectMgmtGraphQLContext context = environment.getContext();
      return context.getUserService().getAllUsers();
    }
  };

  public DataFetcher<Optional<User>> user = new DataFetcher<>() {
    @Override
    public Optional<User> get(DataFetchingEnvironment environment) {
      String userId = environment.getArgument("id");
      ProjectMgmtGraphQLContext context = environment.getContext();
      return context.getUserService().getUser(userId);
    }
  };

  // TODO 1:
  // Füge einen DataFetcher für das 'projects' Feld hinzu
  // - Du musst das ProjectRepository über den Context (ProjectMgmtGraphQLContext)
  // abfragen
  // - Über das ProjectRepository kannst Du die Projekte laden
  // - Das Ergebnis des ProjectRepository Aufrufs (Liste mit Projects) kannst Du
  // hier zurückgeben

  // TODO 2:
  // Füge einen DataFetcher für das 'project' Feld hinzu
  // - Du musst das übergeben 'id'-Argument über das DataFetchingEnvironment
  // auslesen
  // - Du musst das ProjectRepository über den Context (ProjectMgmtGraphQLContext)
  // abfragen
  // - Über das ProjectRepository kannst Du ein einzelnes Projekt an Hand dessen
  // Id laden
  // - Das Ergebnis des ProjectRepository Aufrufs (Project) kannst Du hier
  // zurückgeben
}

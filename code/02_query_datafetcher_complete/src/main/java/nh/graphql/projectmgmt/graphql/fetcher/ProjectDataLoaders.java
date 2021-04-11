package nh.graphql.projectmgmt.graphql.fetcher;

public class ProjectDataLoaders {

  // TODO:
  // Implementiere einen BatchLoaderWithContext
  // - Du benögist entweder eine Funktion, die einen BatchLoaderWithContext
  // zurückliefert
  // oder Du verwendest ein public Field dafür (genau wie bei den DataFetchers)

  //
  // - Die beiden Typ-Parameter für den BatchLoaderWithContext sind 'String' und
  // java.util.Option<User>
  // - Die Methode 'load', die im BatcherLoaderWithContext definiert ist, die Du
  // implementieren musst
  // erwartet zwei Parameter:
  // - 1. Eine Liste mit Strings: Das sind die Keys der Benutzer, die geladen
  // werden sollen
  // - 2. Ein Objekt vom Typ 'BatchLoaderEnvironment', hierüber bekommst Du wieder
  // unseren bekannten Context
  //
  // Innerhalb der 'load-Methode musst Du:
  // CompletableFuture.supplyAsync eine Funktion übergeben
  // - die keine Parameter hat,
  // - innerhalb der Funktion rufst Du für jeden Key aus der 'keys'-Liste den
  // UserService auf
  // und lädst die User
  // - Die geladenen User (bzw Optional<User>) lieferst Du dann zurück
  //
  // Die Signatur der 'load'-Methode ist: CompletionStage<List<Optional<User>>>
  // load(List<String> keys, BatchLoaderEnvironment environment)

  //

}

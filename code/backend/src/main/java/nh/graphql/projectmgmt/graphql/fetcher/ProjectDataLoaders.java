package nh.graphql.projectmgmt.graphql.fetcher;

public class ProjectDataLoaders {

  // TODO:
  // Implement a BatchLoaderWithContext
  // - Du benögist entweder eine Funktion, die einen BatchLoaderWithContext
  // zurückliefert
  // oder Du verwendest ein public Field dafür (genau wie bei den DataFetchers)

  //
  // - The Type-Arguments for the generic BatchLoaderWithContext are 'java.lang.String' and
  // java.util.Optional<User> (as our UserService returns an Optional of User)
  // - You need to implement the 'load' method declared in BatcherLoaderWithContext
  // - The method receives two parameters:
  // - 1. A java.util.List with Strings: This are the keys of Users you should load from the
  //      remote service
  // - 2. An object of Type 'BatchLoaderEnvironment'. You can use this object to receive
  //      the ProjectMgmtGraphQLContext context and from the context the UserService

  //
  // Inside the "load"-method you need to:
  //   Invoke UserService.getUser for each id that is passed to the BatchLoader
  //   Return a List of the loaded user object (as Optionals: List<Optional<User>>)
  //
  // When this works, you can enhance your BatchLoader an make it asynchronus,
  //  so that all users are fetched in parallel:
  //
  //  - You can use CompletableFuture.supplyAsync. This method expects a (Lambda-)function
  //    Move the UserService-Call into this lambda function
  //  - The complete signature of the load method using the CompletableFuture is:
  //     public CompletableFuture<List<Optional<User>>>
  //       load(List<String> keys, BatchLoaderEnvironment environment) { ... }


}

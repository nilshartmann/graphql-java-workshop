package nh.graphql.projectmgmt.graphql.fetcher;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class ProjectDataFetchers {

  // TODO 1: Add a DataFetcher for the 'task'-field of the "Project"-type
  //   - You need to receive the "id" of the task from the Arguments (DateFetchingEnvironment!)
  //   - Having the Id, you can receive a Task by its Id using the TaskRepository
  //   - The TaskRepository is available via the Context
  //   - You can return the Task you have read from the Repository
  //   - as the Schema has declared an optional value for the task-field, you can return
  //     either null or an empty Optional-object if no Task has been found with the given id
  //

  // TODO 2: Implement a DataFetcher for the 'owner'-Field at 'Project'-Type
  // - You have to read the Owner of a project using the Project instance
  //   that you receive via getSource()
  // - with this id you can use the UserService to read a User Object
  // - the UserService is avaible via the Context
  //  - you can return the loaded User


  // TODO 3: Remember to register your DataFetchers in the RuntimeWiring in GraphQLAPIConfiguration!
}

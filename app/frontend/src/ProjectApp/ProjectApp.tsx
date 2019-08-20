import * as React from "react";
import styles from "./ProjectApp.module.scss";
import AddTaskPage from "../AddTaskPage/AddTaskPage";
import { Route, Switch } from "react-router";
import ProjectsPage from "../ProjectsPage";
import TaskListPage from "../TasksListPage";
import { Link } from "react-router-dom";
import { ApolloClient } from "apollo-client";
import { withApollo } from "@apollo/react-hoc";
import TaskPage from "../TaskPage/TaskPage";
import ProjectsPageWithoutApollo from "ProjectsPage/ProjectsPageWithoutApollo";

function Header() {
  return (
    <header className={styles.Header}>
      <Link to={"/"}>Personal Project Planning</Link>
    </header>
  );
}

function Footer() {
  return (
    <footer className={styles.Footer}>
      <a href="https://github.com/nilshartmann/graphql-workshop" target="_blank" rel="noopener noreferrer">
        https://github.com/nilshartmann/graphql-workshop
      </a>
    </footer>
  );
}
interface ProjectAppProps {
  client: ApolloClient<any>;
}

export function ProjectApp({ client }: ProjectAppProps) {
  return (
    <div className={styles.ProjectApp}>
      <Header />
      <main>
        <Switch>
          <Route exact path={"/"} component={ProjectsPage} />
          <Route exact path={"/p"} component={ProjectsPageWithoutApollo} />
          <Route exact path={"/project/:projectId/tasks"} component={TaskListPage} />
          <Route exact path={"/project/:projectId/tasks/:taskId"} component={TaskPage} />
          <Route exact path={"/project/:projectId/addtaks"} component={AddTaskPage} />
        </Switch>
      </main>
      <Footer />
    </div>
  );
}

export default withApollo<{}>(ProjectApp);

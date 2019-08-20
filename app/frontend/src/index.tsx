import * as React from "react";
import * as ReactDOM from "react-dom";

import { ApolloProvider } from "@apollo/react-hooks";
import { HashRouter as Router } from "react-router-dom";

import createApolloClient from "./infra/createApolloClient";
import NavigationProvider from "./infra/NavigationProvider";
import ProjectApp from "./ProjectApp/ProjectApp";

const client = createApolloClient();

const theApp = (
  <ApolloProvider client={client}>
    <Router>
      <NavigationProvider>
        <ProjectApp />
      </NavigationProvider>
    </Router>
  </ApolloProvider>
);

const mountNode = document.getElementById("root");
ReactDOM.render(theApp, mountNode);

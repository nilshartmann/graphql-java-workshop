import * as React from "react";
import styles from "./ProjectsPage.module.scss";
import NavButton from "../components/NavButton";
import { useNavigator } from "../infra/NavigationProvider";
import { ProjectsPageQuery_projects } from "./querytypes/ProjectsPageQuery";

const PROJECTS_PAGE_QUERY = `
  query ProjectsPageQuery {
    projects {
      id
      title
      owner {
        name
      }
      category {
        name
      }
    }
  }
`;

interface ProjectsTableProps {
  projects: ProjectsPageQuery_projects[];
}
function ProjectsTable({ projects }: ProjectsTableProps) {
  const navigator = useNavigator();
  return (
    <table>
      <thead>
        <tr>
          <th>Name</th>
          <th>Owner</th>
          <th>Category</th>
          <th />
        </tr>
      </thead>
      <tbody>
        {projects.map(project => {
          return (
            <tr key={project.id}>
              <td>{project.title}</td>
              <td>{project.owner.name}</td>
              <td>{project.category.name}</td>
              <td>
                <NavButton onClick={() => navigator.openTasksPage(project.id)} />
              </td>
            </tr>
          );
        })}
      </tbody>
    </table>
  );
}

type ProjectsPageState = {
  projects: ProjectsPageQuery_projects[];
};

export default class ProjectsPageWithoutApollo extends React.Component<{}, ProjectsPageState> {
  state = {
    projects: []
  };

  componentDidMount() {
    fetch("http://localhost:4000", {
      method: "POST",
      headers: {
        "content-type": "application/json"
      },
      body: JSON.stringify({
        query: PROJECTS_PAGE_QUERY
      })
    })
      .then(res => res.json())
      .then(({ data }) => this.setState({ projects: data.projects }));
  }

  render() {
    return (
      <div className={styles.ProjectsPage}>
        <header>
          <h1>All Projects</h1>
        </header>
        <ProjectsTable projects={this.state.projects} />;
      </div>
    );
  }
}

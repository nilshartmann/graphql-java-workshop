# VORBEREITUNG VOR DEM WORKSHOP

## Starten Anwendung und Frontend
1. Starten
- UserService über Gradle
- Den Rest über Eclipse
- FE über Command line

2. Lokale IP Adresse ermitteln und aufschreiben
3. Playground öffnen und NewTask Mutation starten (für Übung 1)

# QUERIES

Anzahl an UserService Requests:

query ProjectsPageQuery {
  projects {
    id

    owner {
      id
    }
    tasks {
      assignee {
        id
        name
      }
    }
  }
}

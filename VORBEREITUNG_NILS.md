# VORBEREITUNG VOR DEM WORKSHOP

## Starten Anwendung und Frontend

### 1. UserService

```
cd /Users/nils/develop/javascript/graphql-java-workshop/app/userservice
./gradlew clean bootRun
```

### 2. Backend

- Über Eclipse (SpringToolSuite 4)

### 3. Frontend

```
cd /Users/nils/develop/javascript/graphql-java-workshop/app/frontend
yarn start
```

### 4. ngrok

```
ngrok http 5000
```

### 5. Hostname aufschreiben

### 6. Playground öffnen und NewTask Mutation in eigenem Tab starten (für Übung 1)

```
subscription {
  newTask: onNewTask {
    id
    title
    description
    assignee {
      login
    }
  }
}
```

# QUERIES

## Datenbank Joins

### Nur Projekte, kein Join notwendig:

```
query ProjectsOnly {projects { id }}
```

### Projekte und Category, Join notwendig, durch Lazy Loading x Queries

```
query ProjectsAndCategory {
  projects {
    id
    category {
      name
    }
  }
}
```

### Projekte und Task, Join notwendig, durch Lazy Loading x Queries

```
query ProjectsAndTasks {
  projects {
    tasks {
      title
    }
  }
}

```

### Projekte mit Tasks und Kategorien, Join notwendig, durch Lazy Loading x Queries

```
query ProjectsAndTasksWithCategory {
  projects {
    category {
      name
    }
    tasks {
      title
    }
  }
}
```

### Projekte mit Tasks und Kategorien, Join notwendig, durch Entity Graph nur 1 Query:

```
query ProjectsAndTasksWithCategory {
  projects @useEntityGraph {
    category {
      name
    }
    tasks {
      title
    }
  }
}
```

## UserService / DataLoader

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

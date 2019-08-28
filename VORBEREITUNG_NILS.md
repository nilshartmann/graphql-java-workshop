# VORBEREITUNG VOR DEM WORKSHOP

## Starten Anwendung und Frontend
1. Starten
- UserService über Gradle
- Den Rest über Eclipse
- FE über Command line

2. Lokale IP Adresse ermitteln und aufschreiben
3. Playground öffnen und NewTask Mutation starten (für Übung 1)

# QUERIES

## Datenbank Joins
### Nur Projekte, kein Join notwendig:
```
query ProjectsOnly {projects { id }}
```

SQL:
```
Hibernate: /* SELECT p FROM Project p ORDER BY p.id */ select project0_.id as id1_1_, project0_.category_id as category5_1_, project0_.description as descript2_1_, project0_.owner_id as owner_id3_1_, project0_.title as title4_1_ from projects project0_ order by project0_.id
```


### Projekte und Category, Join notwendig:
```
query ProjectsAndCategory {
  projects {
    id
    category {
      id
    }
  }
}
```

SQL:
```

Hibernate: /* SELECT p FROM Project p ORDER BY p.id */ select project0_.id as id1_1_0_, category1_.id as id1_0_1_, project0_.category_id as category5_1_0_, project0_.description as descript2_1_0_, project0_.owner_id as owner_id3_1_0_, project0_.title as title4_1_0_, category1_.name as name2_0_1_ from projects project0_ left outer join categories category1_ on project0_.category_id=category1_.id order by project0_.id
```

### Projekte und Task, Join notwendig:

```
query ProjectsAndTasks {
  projects {
    tasks {
      id
    }
  }
}

```

SQL
```
Hibernate: /* SELECT p FROM Project p ORDER BY p.id */ select project0_.id as id1_1_0_, task2_.id as id1_3_1_, project0_.category_id as category5_1_0_, project0_.description as descript2_1_0_, project0_.owner_id as owner_id3_1_0_, project0_.title as title4_1_0_, task2_.assignee_id as assignee2_3_1_, task2_.description as descript3_3_1_, task2_.state as state4_3_1_, task2_.title as title5_3_1_, task2_.finish_date as finish_d6_3_1_, tasks1_.project_id as project_1_2_0__, tasks1_.tasks_id as tasks_id2_2_0__ from projects project0_ left outer join projects_tasks tasks1_ on project0_.id=tasks1_.project_id left outer join tasks task2_ on tasks1_.tasks_id=task2_.id order by project0_.id, task2_.id

```

### Projekte mit Tasks und Kategorien, Join notwendig:
```
query ProjectsAndTasksWithCategory {
  projects {
    category {
      id
    }
    tasks {
      id
    }
  }
}
```

SQL
```
Hibernate: /* SELECT p FROM Project p ORDER BY p.id */ select project0_.id as id1_1_0_, category1_.id as id1_0_1_, task3_.id as id1_3_2_, project0_.category_id as category5_1_0_, project0_.description as descript2_1_0_, project0_.owner_id as owner_id3_1_0_, project0_.title as title4_1_0_, category1_.name as name2_0_1_, task3_.assignee_id as assignee2_3_2_, task3_.description as descript3_3_2_, task3_.state as state4_3_2_, task3_.title as title5_3_2_, task3_.finish_date as finish_d6_3_2_, tasks2_.project_id as project_1_2_0__, tasks2_.tasks_id as tasks_id2_2_0__ from projects project0_ left outer join categories category1_ on project0_.category_id=category1_.id left outer join projects_tasks tasks2_ on project0_.id=tasks2_.project_id left outer join tasks task3_ on tasks2_.tasks_id=task3_.id order by project0_.id, task3_.id
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

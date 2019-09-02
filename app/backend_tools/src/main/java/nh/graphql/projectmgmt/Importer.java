package nh.graphql.projectmgmt;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import nh.graphql.projectmgmt.domain.Category;
import nh.graphql.projectmgmt.domain.CategoryRepository;
import nh.graphql.projectmgmt.domain.Project;
import nh.graphql.projectmgmt.domain.ProjectRepository;
import nh.graphql.projectmgmt.domain.Task;
import nh.graphql.projectmgmt.domain.TaskState;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Repository
public class Importer {

  Logger logger = LoggerFactory.getLogger(Importer.class);

//	@Autowired
//	private TaskRepository taskRepository;

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  private final static Random random = new Random(666);
  private final Lorem lorem = new LoremIpsum(667L);

  private String rd() {
    return lorem.getWords(10, 50);
  }

  private LocalDateTime dt(String d) {

    int hours = randomBetween(0, 23);
    int minutes = randomBetween(0, 59);

    if (d == null || "".equals(d)) {
      return LocalDateTime.of(2019, Month.APRIL, 3, hours, minutes).plusDays(randomBetween(10, 90));
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    return LocalDateTime.parse(String.format("%s %s:%s", d, hours, minutes), formatter);
  }

  @Transactional
  public void addDummies() {
    Category c1 = categoryRepository.save(new Category("Private"));
    for (int i = 1; i <= 17; i++) {
      Project p = new Project("U1", c1, "Project-" + i, "PROJECT DESCRIPTION  " + i);
      p.addTask(new Task("U2", "A Task for Project #" + i, rd(), LocalDateTime.now()));

      projectRepository.save(p);
    }
  }

  @Transactional
  public void add() {

    Category c1 = categoryRepository.save(new Category("Private"));
    Category c2 = categoryRepository.save(new Category("Hobby"));
    Category c3 = categoryRepository.save(new Category("Business"));

    Project p1 = projectRepository.save(new Project("U1", c3, "Create GraphQL Talk", "Create GraphQL Talk"));
    Project p2 = projectRepository.save(new Project("U2", c2, "Book Trip to St. Peter-Ording",
        "Organize and book a nice 4-day trip to the North Sea in April"));
    Project p3 = projectRepository
        .save(new Project("U3", c1, "Clean the House", "Its spring time! Time to clean up every room"));
    Project p4 = projectRepository.save(new Project("U1", c3, "Refactor Application",
        "We have some problems in our architecture, so we need to refactor it"));
    Project p5 = projectRepository.save(new Project("U1", c3, "Tax Declaration", "Same procedure as every year..."));
    Project p6 = projectRepository.save(
        new Project("U4", c3, "Implement GraphQL Java App", "Implement a small application to demonstrate GraphQL"));

    Task t1 = new Task("U1", "Create a draft story", rd(), TaskState.RUNNING, dt(""));
    Task t2 = new Task("U2", "Finish Example App", rd(), dt(""));
    Task t3 = new Task("U1", "Design Slides", rd(), dt(""));
    p1.addTasks(t1, t2, t3);

    Task t4 = new Task("U2", "Find a train", rd(), TaskState.NEW, dt(""));
    Task t5 = new Task("U1", "Book a room", rd(), TaskState.FINISHED, dt(""));
    p2.addTasks(t4, t5);

    Task t6 = new Task("U3", "Clean dining room", rd(), dt(""));
    Task t7 = new Task("U1", "Clean kitchen", rd(), dt(""));
    Task t8 = new Task("U2", "Empty trash bin", rd(), TaskState.FINISHED, dt(""));
    Task t9 = new Task("U2", "Clean windows", rd(), TaskState.RUNNING, dt(""));
    p3.addTasks(t6, t7, t8, t9);

    Task t10 = new Task("U5", "Discuss problems with all developers", rd(), TaskState.RUNNING, dt(""));
    Task t11 = new Task("U6", "Evaluate GraphQL for API", rd(), TaskState.RUNNING, dt(""));
    Task t12 = new Task("U5", "Re-write tests in Jest", rd(), TaskState.RUNNING, dt(""));
    Task t13 = new Task("U1", "Upgrade NodeJS version", rd(), TaskState.FINISHED, dt(""));
    p4.addTasks(t10, t11, t12, t13);

    Task t14 = new Task("U1", "Print invoices", rd(), dt(""));
    Task t15 = new Task("U1", "Collect receipts of last quarter", rd(), dt(""));
    Task t16 = new Task("U1", "Mail to tax accountant", rd(), dt(""));
    p5.addTasks(t14, t15, t16);

    Task t17 = new Task("U1", "Create sample user stories", rd(), dt(""));
    Task t18 = new Task("U2", "Design user interface", rd(), dt(""));
    Task t19 = new Task("U3", "Create initial Java Project", rd(), dt(""));
    Task t20 = new Task("U4", "Add GraphQL libs", rd(), dt(""));
    Task t21 = new Task("U7", "Implement Domain model", rd(), dt(""));
    Task t22 = new Task("U8", "Add persistence", rd(), dt(""));
    Task t23 = new Task("U3", "Write some test", rd(), dt(""));
    Task t24 = new Task("U2", "Define GraphQL Schema", rd(), dt(""));
    Task t25 = new Task("U3", "Implement DataLoader", rd(), dt(""));
    Task t26 = new Task("U5", "Add pagination support", rd(), dt(""));
    Task t27 = new Task("U6", "Add login mutation", rd(), dt(""));
    Task t28 = new Task("U7", "Write documentation", rd(), dt(""));
    Task t29 = new Task("U7", "Setup testdata", rd(), dt(""));
    Task t30 = new Task("U8", "Optimize data loaders", rd(), dt(""));
    p6.addTasks(t17, t18, t19, t20, t21, t22, t23, t24, t25, t26, t27, t28, t29, t30);

  }

  private static int randomBetween(int min, int max) {
    return random.nextInt((max - min) + 1) + min;
  }
}

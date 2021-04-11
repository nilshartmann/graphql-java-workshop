package nh.graphql.projectmgmt.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Entity
@Table(name = "categories")
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_generator")
  @SequenceGenerator(name = "category_id_generator", sequenceName = "category_id_seq", initialValue = 8000)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @NotNull
  @Column(name = "name", nullable = false)
  private String name;

  protected Category() {
  }

  public Category(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }
}

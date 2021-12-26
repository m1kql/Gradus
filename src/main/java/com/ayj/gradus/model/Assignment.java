package com.ayj.gradus.model;

import java.util.Date;

import javax.persistence.*;

@Entity
public class Assignment {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String author;

  private int progress;

  private Date dueDate;

  private String description;

  public Assignment() {
    super();
  }

  public Assignment(String author, int progress, Date dueDate, String description, boolean isDone) {
    super();
    this.author = author;
    this.description = description;
    this.dueDate = dueDate;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAuthor() {
    return this.author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public int getProgress() {
    return this.progress;
  }

  public void setProgress(int progress) {
    this.progress = progress;
  }

  public Date getDueDate() {
    return this.dueDate;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}

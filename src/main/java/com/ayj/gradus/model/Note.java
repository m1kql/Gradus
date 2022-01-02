package com.ayj.gradus.model;

import javax.persistence.*;

@Entity
public class Note {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String author;

  private String note;

  public Note() {
    super();
  }

  public Note(Long id, String author, String note) {
    super();
    this.id = id;
    this.author = author;
    this.note = note;
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

  public String getNote() {
    return this.note;
  }

  public void setNote(String note) {
    this.note = note;
  }

}
package com.ayj.gradus.model;

import javax.persistence.*;

@Entity
public class Note {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String author;

  private String noteTitle;

  private String noteDescription;

  private boolean isImportant;

  public Note() {
    super();
  }

  public Note(String author, String noteTitle, String noteDescription, boolean isImportant) {
    super();
    this.author = author;
    this.noteTitle = noteTitle;
    this.noteDescription = noteDescription;
    this.isImportant = isImportant;
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

  public String getNoteTitle() {
    return this.noteTitle;
  }

  public void setNoteTitle(String noteTitle) {
    this.noteTitle = noteTitle;
  }

  public String getNoteDescription() {
    return this.noteDescription;
  }

  public void setNoteDescription(String noteDescription) {
    this.noteDescription = noteDescription;
  }

  public boolean getIsImportant() {
    return this.isImportant;
  }

  public void setIsImportant(boolean isImportant) {
    this.isImportant = isImportant;
  }
}
package com.ayj.gradus.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ayj.gradus.model.Assignment;

/**
 * This interface contains methods used in the CRUD operations of the Gradus app
 * 
 * 
 * @see createAssignment()
 * @see getAssignmentById()
 * @see updateAssignment()
 * @see deleteAssignment()
 */
public interface AssignmentService {

  List<Assignment> getAssignmentsByUser(String author);

  Optional<Assignment> getAssignmentById(long id);

  /**
   * 
   * 
   * 
   * 
   * @param author
   * @param progress
   * @param dueDate
   * @param description
   * @param isDone
   */
  void createAssignment(String author, int progress, Date dueDate, String description, boolean isDone);

  void updateAssignment(Assignment assignment);

  void deleteAssignment(long id);

  void saveAssignment(Assignment assignment);

}

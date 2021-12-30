package com.ayj.gradus.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ayj.gradus.model.Assignment;
import com.ayj.gradus.repositories.AssignmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomAssignmentService implements AssignmentService {

  @Autowired
  private AssignmentRepository assignmentRepository;

  @Autowired
  public CustomAssignmentService(AssignmentRepository assignmentRepository) {
    this.assignmentRepository = assignmentRepository;
  }

  @Override
  public List<Assignment> getAssignmentsByUser(String author) {
    return assignmentRepository.findByAuthor(author);
  }

  @Override
  public Optional<Assignment> getAssignmentById(long id) {
    return assignmentRepository.findById(id);
  }

  @Override
  public void createAssignment(String author, int progress, Date dueDate, String description, boolean isDone) {
    assignmentRepository.save(new Assignment(author, progress, dueDate, description, isDone));
  }

  @Override
  public void updateAssignment(Assignment assignment) {
    assignmentRepository.save(assignment);
  }

  @Override
  public void deleteAssignment(long id) {
    Optional<Assignment> aOptional = assignmentRepository.findById(id);
    if (aOptional.isPresent()) {
      assignmentRepository.delete(aOptional.get());
    }
  }

  @Override
  public void saveAssignment(Assignment assignment) {
    assignmentRepository.save(assignment);
  }

}

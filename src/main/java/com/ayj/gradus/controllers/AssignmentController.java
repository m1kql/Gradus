package com.ayj.gradus.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import com.ayj.gradus.model.Assignment;
import com.ayj.gradus.services.CustomAssignmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class AssignmentController {

  @Autowired
  private CustomAssignmentService assignmentService;

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
  }

  @GetMapping("/assignments")
  public ModelAndView assignmentsList(ModelAndView modelAndView, Assignment assignment) {

    String currUserEmail;
    String date;

    currUserEmail = getUserEmail(modelAndView);
    date = getDate();

    modelAndView.addObject("username", currUserEmail);
    modelAndView.addObject("assignment", assignment);
    modelAndView.addObject("assignments", assignmentService.getAssignmentsByUser(currUserEmail));
    modelAndView.addObject("current_date", date);

    modelAndView.setViewName("pages/assignments");

    return modelAndView;
  }

  @PostMapping("/assignments")
  public ModelAndView addAssignment(ModelAndView modelAndView, @Valid Assignment assignment,
      BindingResult bindingResult) {

    String description;
    int progress;
    Date dueDate;
    String currUserEmail;

    currUserEmail = getUserEmail(modelAndView);

    description = assignment.getDescription();
    progress = assignment.getProgress();
    dueDate = assignment.getDueDate();

    assignment.setDescription(description);
    assignment.setProgress(progress);
    assignment.setDueDate(dueDate);
    assignment.setAuthor(currUserEmail);

    System.out.println(dueDate);

    assignmentService.saveAssignment(assignment);

    return new ModelAndView("redirect:/assignments");
  }

  @GetMapping("/edit-assignment")
  public ModelAndView editAssignmentPage(ModelAndView modelAndView, @RequestParam long id) {

    Assignment assignment = assignmentService.getAssignmentById(id).get();

    modelAndView.addObject("assignment", assignment);

    modelAndView.setViewName("pages/edit");

    return modelAndView;
  }

  @PostMapping("/edit-assignment")
  public ModelAndView editAssignment(@RequestParam long id, @RequestParam String description, ModelAndView modelAndView,
      @Valid Assignment assignment,
      BindingResult bindingResult) {

    String currUserEmail;
    Date dueDate;
    currUserEmail = getUserEmail(modelAndView);
    dueDate = assignment.getDueDate();

    assignment.setAuthor(currUserEmail);
    assignment.setId(id);
    assignment.setDescription(description);
    assignment.setDueDate(dueDate);

    assignmentService.updateAssignment(assignment);

    return new ModelAndView("redirect:/assignments");
  }

  @GetMapping("/assignments/delete")
  public ModelAndView deleteAssignment(@RequestParam long id, ModelAndView modelAndView) {

    assignmentService.deleteAssignment(id);
    modelAndView.setViewName("pages/assignments");

    return new ModelAndView("redirect:/assignments");
  }

  private String getUserEmail(ModelAndView modelAndView) {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String userEmail = auth.getName();

    return userEmail;
  }

  private String getDate() {
    String formattedDateString;

    formattedDateString = String.valueOf(java.time.LocalDate.now());

    return formattedDateString;
  }

}

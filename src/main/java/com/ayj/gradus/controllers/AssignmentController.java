package com.ayj.gradus.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import com.ayj.gradus.model.Assignment;
import com.ayj.gradus.services.CustomAssignmentService;
import com.ayj.gradus.utils.CommonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Assignment Controller
 */
@Controller
public class AssignmentController {

  /**
   * Dependency injection for these objects
   */
  @Autowired
  private CustomAssignmentService assignmentService;

  @Autowired
  private CommonUtils commonUtils;

  /**
   * Init binder so that we can parse the date submitted in the form datas
   * 
   * @param binder
   */
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
  }

  /**
   * Method to facilitate GET requests to this endpoint
   * 
   * @param modelAndView - the placeholder for the model object and view
   * @param assignment   - a placeholder object for assignment attributes
   * @return modelAndView - A holder that contains both the view and model
   */
  @GetMapping("/assignments")
  public ModelAndView assignmentsList(ModelAndView modelAndView, Assignment assignment) {

    String currUserEmail;
    String date;

    currUserEmail = commonUtils.getUserEmail(modelAndView);
    date = commonUtils.getDate();

    modelAndView.addObject("username", currUserEmail);
    modelAndView.addObject("assignment", assignment);
    modelAndView.addObject("assignments", assignmentService.getAssignmentsByUser(currUserEmail));
    modelAndView.addObject("current_date", date);

    modelAndView.setViewName("pages/assignments");

    return modelAndView;
  }

  /**
   * Method to facilitate POST requests to this endpoint and create an assignment
   * object
   * 
   * @param modelAndView  - a placeholder for the model object and view
   * @param assignment    - assignment object
   * @param bindingResult - an object as an argument to validate form data
   * @return ModelAndView - a new model and view object containing a redirect to a
   *         new endpoint
   */
  @PostMapping("/assignments")
  public ModelAndView addAssignment(ModelAndView modelAndView, @Valid Assignment assignment,
      BindingResult bindingResult) {

    // attributes of the assignment object
    String description;
    int progress;
    Date dueDate;

    String currUserEmail;

    // obtain all the necessary info

    currUserEmail = commonUtils.getUserEmail(modelAndView);

    description = assignment.getDescription();
    progress = assignment.getProgress();
    dueDate = assignment.getDueDate();

    assignment.setDescription(description);
    assignment.setProgress(progress);
    assignment.setDueDate(dueDate);
    assignment.setAuthor(currUserEmail);

    assignmentService.saveAssignment(assignment);

    return new ModelAndView("redirect:/assignments");
  }

  /**
   * A method that facilitates GET requests to this endpoint
   * 
   * @param modelAndView - a placeholder for the model object and view
   * @param id           - id of the assignment in the URL
   * @return modelAndView - a model and view object
   */
  @GetMapping("/edit-assignment")
  public ModelAndView editAssignmentPage(ModelAndView modelAndView, @RequestParam long id) {

    Assignment assignment = assignmentService.getAssignmentById(id).get();

    modelAndView.addObject("assignment", assignment);

    modelAndView.setViewName("pages/edit");

    return modelAndView;
  }

  /**
   * A mehod that facilitates POST requests to this endpoint
   * 
   * @param id            - id of the assignment in the url
   * @param description   - the description attribute of the assignment
   * @param modelAndView  - a placeholder for the model object and view
   * @param assignment    - an assignment object
   * @param bindingResult - a bindingresult to reject any errors
   * @return ModelAndView - a new model and view object containing a redirect to a
   *         new endpoint
   */
  @PostMapping("/edit-assignment")
  public ModelAndView editAssignment(@RequestParam long id, @RequestParam String description, ModelAndView modelAndView,
      @Valid Assignment assignment,
      BindingResult bindingResult) {

    String currUserEmail;
    Date dueDate;
    currUserEmail = commonUtils.getUserEmail(modelAndView);
    dueDate = assignment.getDueDate();

    assignment.setAuthor(currUserEmail);
    assignment.setId(id);
    assignment.setDescription(description);
    assignment.setDueDate(dueDate);

    assignmentService.updateAssignment(assignment);

    return new ModelAndView("redirect:/assignments");
  }

  /**
   * A method that facilitates GET requests to this endpoint
   * 
   * @param id           - id of the assignment
   * @param modelAndView - a placeholder for a model object and views
   * @return ModelAndView - a new model and view object containing a redirect to a
   *         new endpoint
   */
  @GetMapping("/assignments/delete")
  public ModelAndView deleteAssignment(@RequestParam long id, ModelAndView modelAndView) {

    assignmentService.deleteAssignment(id);
    modelAndView.setViewName("pages/assignments");

    return new ModelAndView("redirect:/assignments");
  }

}

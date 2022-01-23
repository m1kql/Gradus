package com.ayj.gradus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import com.ayj.gradus.model.User;
import com.ayj.gradus.services.UserService;

@Controller
public class AuthController {

  @Autowired
  private UserService userService;

  /**
   * Login GET endpoint
   * 
   * @param modelAndView
   * @return
   */
  @GetMapping("/login")
  public ModelAndView login(ModelAndView modelAndView) {
    modelAndView.setViewName("public/login");
    return modelAndView;
  }

  /**
   * Register GET endpoint
   * 
   * @param modelAndView
   * @param user         - user object for form data
   * @return
   */
  @GetMapping("/register")
  public ModelAndView registration(ModelAndView modelAndView, User user) {
    modelAndView.addObject("user", user);
    modelAndView.setViewName("public/register");
    return modelAndView;
  }

  @PostMapping("/register")
  public ModelAndView createNewUser(ModelAndView modelAndView, @Valid User user, BindingResult bindingResult) {

    String email;
    email = user.getUsername().toString(); // Sanitise it and make sure that it is a string

    User userExists = userService.findUserByUsername(email);

    if (userExists != null) {
      bindingResult
          .reject("username");
    }
    if (bindingResult.hasErrors()) {
      modelAndView.addObject("existing_account_message",
          "There is an existing account with the same email. Please provide a different email.");
      modelAndView.setViewName("public/register");
    } else {
      if (!(email.contains("tdsb.on.ca"))) {
        bindingResult.reject("email");
      }
      if (bindingResult.hasErrors()) {
        modelAndView.addObject("invalid_email_message", "This email is not a valid TDSB email.");
        modelAndView.setViewName("public/register");
      } else {
        userService.saveUser(user);
        modelAndView.addObject("creation_success_message", "Your account was successfully created.");
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("public/register");
      }
    }
    return modelAndView;
  }

}

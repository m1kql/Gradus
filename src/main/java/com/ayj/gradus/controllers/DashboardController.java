package com.ayj.gradus.controllers;

import com.ayj.gradus.model.User;
import com.ayj.gradus.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {

  @Autowired
  private UserService userService;

  @GetMapping("/user/home")
  public ModelAndView home(ModelAndView modelAndView) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = userService.findUserByUsername(auth.getName());
    modelAndView.addObject("user_email", user.getUsername());
    modelAndView.setViewName("pages/home");
    return modelAndView;
  }

}

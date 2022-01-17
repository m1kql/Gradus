package com.ayj.gradus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PublicController {

  private final String VERSION = "v0.1.0-alpha";

  @GetMapping("/")
  public ModelAndView index(ModelAndView modelAndView) {
    modelAndView.addObject("version", VERSION);
    modelAndView.setViewName("public/index");
    return modelAndView;
  }

  @GetMapping("/docs")
  public ModelAndView docs(ModelAndView modelAndView) {

    modelAndView.setViewName("public/docs");

    return modelAndView;
  }

}

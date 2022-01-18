package com.ayj.gradus.controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.ayj.gradus.model.User;
import com.ayj.gradus.services.CustomAssignmentService;
import com.ayj.gradus.services.UserService;
import com.ayj.gradus.utils.CommonUtils;

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

  @Autowired
  private CustomAssignmentService assignmentService;

  @Autowired
  private CommonUtils commonUtils;

  @GetMapping("/home")
  public ModelAndView home(ModelAndView modelAndView) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = userService.findUserByUsername(auth.getName());

    String currentDate;
    String userEmail;
    String userFirstName;
    String formattedUserFirstName;
    String rawLine;
    String quote;
    String author;
    String currUserEmail;
    ArrayList<String> quoteObject = new ArrayList<>();
    int randomLine;
    boolean hasAssignments = false;

    currentDate = commonUtils.getDate();
    userEmail = user.getUsername();

    userFirstName = userEmail
        .split("@")[0]
            .replaceAll("\\d", "")
            .split("\\.")[0];

    formattedUserFirstName = userFirstName
        .substring(0, 1)
        .toUpperCase() + userFirstName.substring(1);

    quoteObject = getQuotes();
    randomLine = (int) Math.floor(Math.random() * quoteObject.size());

    rawLine = quoteObject.get(randomLine);
    quote = rawLine.split("\\|")[0];
    author = rawLine.split("\\|")[1];

    currUserEmail = commonUtils.getUserEmail(modelAndView);

    try {
      assignmentService.getAssignmentsByUser(currUserEmail);
      hasAssignments = true;
    } catch (Exception exception) {
      hasAssignments = false;
    }

    modelAndView.addObject("quote", quote);
    modelAndView.addObject("quote_author", author);
    modelAndView.addObject("user_fName", formattedUserFirstName);
    modelAndView.addObject("user_email", userEmail);
    modelAndView.addObject("current_date", currentDate);
    modelAndView.addObject("assignments",
        hasAssignments ? assignmentService.getAssignmentsByUser(currUserEmail) : null);
    modelAndView.setViewName("pages/home");

    return modelAndView;
  }

  private ArrayList<String> getQuotes() {

    BufferedReader bReader = null;
    ArrayList<String> quoteObject = new ArrayList<>();
    try {
      bReader = new BufferedReader(new FileReader("src/main/resources/static/quotes.txt"));

      String currentLine = "";

      while ((currentLine = bReader.readLine()) != null) {
        currentLine = bReader.readLine();
        quoteObject.add(currentLine);
      }
    } catch (FileNotFoundException fileNotFoundException) {
      fileNotFoundException.printStackTrace();

    } catch (IOException ioException) {
      ioException.printStackTrace();
    }

    return quoteObject;
  }

}

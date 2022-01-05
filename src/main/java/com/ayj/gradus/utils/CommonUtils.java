package com.ayj.gradus.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * 
 * 
 */
public class CommonUtils {

  /**
   * 
   * @return
   */
  public String getDate() {
    String formattedDateString;

    formattedDateString = String.valueOf(java.time.LocalDate.now());

    return formattedDateString;
  }

  /**
   * 
   * @param modelAndView
   * @return
   */
  public String getUserEmail(ModelAndView modelAndView) {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String userEmail = auth.getName();

    return userEmail;
  }

}

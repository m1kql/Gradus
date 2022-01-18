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

  private String os = System.getProperty("os.name").toLowerCase();

  public String UPLOAD_DIR;

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

  /**
   * 
   * @return
   */
  public boolean isWindows() {
    return (os.indexOf("win") >= 0);
  }

  /**
   * 
   * @return
   */
  public boolean isUNIX() {
    return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") > 0);
  }

  /**
   * 
   * @return
   */
  public String determineOS() {
    boolean isWindows = isWindows();
    boolean isUNIX = isUNIX();
    if (isWindows) {
      return "windows";
    } else if (isUNIX) {
      return "unix <3";
    } else {
      return "";
    }
  }

  public void createOSPath() throws Exception {

    String os = determineOS();

    if (os.equals("windows")) {

      this.UPLOAD_DIR = System.getProperty("user.home") + "\\Gradus\\uploads\\";

    } else if (os.equals("unix <3")) {

      this.UPLOAD_DIR = "~/Gradus/uploads";

    } else {
      throw new Exception("Unrecognised OS");
    }

  }

  public String getUploadDir() throws Exception {

    createOSPath();

    return this.UPLOAD_DIR;

  }

}

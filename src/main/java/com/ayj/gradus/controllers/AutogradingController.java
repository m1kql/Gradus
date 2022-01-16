package com.ayj.gradus.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeMap;

import com.ayj.gradus.utils.CommonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AutogradingController {

  @Autowired
  private CommonUtils commonUtils;

  @GetMapping("autograding/upload")
  public ModelAndView uploadView(ModelAndView modelAndView) {

    modelAndView.setViewName("pages/upload");

    return modelAndView;

  }

  @PostMapping("autograding/upload")
  public ModelAndView uploadFile(@RequestParam("autograde_file") MultipartFile file, ModelAndView modelAndView)
      throws Exception {

    String currUserEmail;
    String fileName;

    if (file.isEmpty()) {

      modelAndView.addObject("empty_file", "File is empty");
      modelAndView.setViewName("pages/upload");

    }

    currUserEmail = commonUtils.getUserEmail(modelAndView);

    fileName = StringUtils.cleanPath(currUserEmail.split("@")[0]
        .replaceAll("\\d", "")
        .split("\\.")[0]
        + "_"
        + String.valueOf(LocalDate.now())
            .replaceAll("-", "")
        + ".txt");

    try {

      new File(commonUtils.getUploadDir() + fileName).mkdirs();

      Path path = Paths.get(commonUtils.getUploadDir() + fileName);
      Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

    } catch (IOException e) {
      e.printStackTrace();
    }

    modelAndView.addObject("file_name",
        fileName
            .replace(".txt", ""));

    if (!file.isEmpty()) {
      modelAndView.addObject("success_message", "File is uploaded. Ready for processing!");
    }

    modelAndView.setViewName("pages/upload");

    return modelAndView;

  }

  @GetMapping("/autograding/complete/download")
  @ResponseBody
  public FileSystemResource reportView(@RequestParam("ou") String fileName)
      throws Exception {

    ArrayList<ArrayList<Object>> fileInfo = new ArrayList<ArrayList<Object>>();
    ArrayList<Object> tempCourses = new ArrayList<>();
    ArrayList<Object> tempGrades = new ArrayList<>();
    ArrayList<String> courses = new ArrayList<>();
    ArrayList<Double> grades = new ArrayList<>();
    String filePath;
    String currUserEmail;
    String temporaryFileLocation;

    filePath = commonUtils.getUploadDir() + fileName + ".txt";

    fileInfo = readFile(filePath);

    tempCourses = fileInfo.get(0);
    tempGrades = fileInfo.get(1);

    for (Object course : tempCourses) {
      courses.add(course.toString());
    }

    for (Object grade : tempGrades) {
      grades.add(Double.parseDouble(grade.toString()));
    }

    ModelAndView modelAndView = new ModelAndView();

    currUserEmail = commonUtils.getUserEmail(modelAndView);

    temporaryFileLocation = commonUtils.getUploadDir() + currUserEmail.split("@")[0]
        .replaceAll("\\d", "")
        .split("\\.")[0]
        + "_"
        + String.valueOf(LocalDate.now())
            .replaceAll("-", "")
        + ".txt";

    FileWriter compiledReportWriter = new FileWriter(temporaryFileLocation);

    final String ASCII_DIVIDER = "================================================";
    final String TITLE = "AUTO-GENERATED REPORT";
    final String FOOTER_TEXT = "generated with <a href='https://github.com/yak-fumblepack/Gradus'>Gradus</a>.<br/><a href='/'>Go Back To HomePage</a>";
    final String LINE_BREAK = "<br/>\n";
    final String PRINT_BUTTON = "<button onclick='window.print()'>Print this page</button>";

    String graph = "";
    String[] bestCourse;
    double average = 0;
    double median = 0;

    graph = drawGraph(courses, grades);

    average = calculateAverage(grades);
    average = Math.round(average * 100.0) / 100.0; // Round it to two decimal
    // places

    median = calculateMedian(grades);
    median = Math.round(median * 100.0) / 100.0; // Round it to two decimal
    // places

    bestCourse = findBestCourse(courses, grades);

    String formattedHeader = ASCII_DIVIDER
        + LINE_BREAK
        + TITLE
        + LINE_BREAK
        + currUserEmail
        + LINE_BREAK
        + LocalDateTime.now()
        + LINE_BREAK
        + ASCII_DIVIDER;

    // graph = drawGraph(courses, grades);

    String formattedBody = LINE_BREAK
        + LINE_BREAK
        + "Grades Summary:"
        + LINE_BREAK
        + "Average: " + average
        + LINE_BREAK
        + "Median: " + median
        + LINE_BREAK
        + "Best Course: " + bestCourse[0] + " with a grade of: " + bestCourse[1]
        + LINE_BREAK
        + LINE_BREAK
        + "Graph:"
        + LINE_BREAK
        + graph;

    String finalDocument = formattedHeader
        + LINE_BREAK
        + formattedBody
        + LINE_BREAK
        + LINE_BREAK
        + LINE_BREAK
        + LINE_BREAK
        + PRINT_BUTTON
        + LINE_BREAK
        + "File Location: "
        + temporaryFileLocation
        + LINE_BREAK
        + LINE_BREAK
        + LINE_BREAK
        + FOOTER_TEXT;

    compiledReportWriter.write(finalDocument);
    compiledReportWriter.close();

    return new FileSystemResource(new File(temporaryFileLocation));

  }

  private ArrayList<ArrayList<Object>> readFile(String filePath) {

    ArrayList<ArrayList<Object>> fileInfo = new ArrayList<>();
    ArrayList<Object> courses = new ArrayList<>();
    ArrayList<Object> grades = new ArrayList<>();
    String line;

    File file = new File(filePath);

    BufferedReader bufferedReader;
    try {
      bufferedReader = new BufferedReader(new FileReader(file));
      while ((line = bufferedReader.readLine()) != null) {

        String[] temp;

        if (!String.valueOf(line.charAt(0)).equals("#")) {

          temp = line.split("\\|");
          grades.add(temp[1]);
          courses.add(temp[0]);

        }

      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    fileInfo.add(courses);
    fileInfo.add(grades);

    return fileInfo;

  }

  private Double calculateAverage(ArrayList<Double> grades) {

    double sum;
    double average;

    sum = 0;

    for (int i = 0; i < grades.size() - 1; i++) {
      sum += grades.get(i);
    }

    average = sum / grades.size();

    return average;

  }

  private Double calculateMedian(ArrayList<Double> grades) {

    double median;

    if (grades.size() % 2 == 1) {
      median = grades.get(grades.size() / 2);
    } else {
      median = (grades.get(grades.size() / 2) + grades.get((grades.size() / 2) - 1)) / 2;
    }

    return median;

  }

  private String[] findBestCourse(ArrayList<String> courses, ArrayList<Double> grades) {

    Set<Double> tempKeySet;
    String[] bestCourse = new String[2];
    TreeMap<Double, String> studentCourseMap = new TreeMap<>();

    for (int i = 0; i < courses.size() - 1; i++) {
      studentCourseMap.put(grades.get(i), courses.get(i));
    }

    tempKeySet = studentCourseMap.keySet();

    double tempMax = Collections.max(tempKeySet);

    bestCourse[0] = studentCourseMap.get(tempMax);
    bestCourse[1] = String.valueOf(tempMax);

    return bestCourse;

  }

  private String drawGraph(ArrayList<String> courses, ArrayList<Double> grades) {

    String graph = "";

    for (int i = 0; i < courses.size(); i++) {

      graph += String.format("%-24s", courses.get(i));

      for (int j = 0; j < (grades.get(i) / 10); j++) {
        graph += "[][][][]";
      }
      graph += "<br/>\n";
    }

    return graph;

  }

}

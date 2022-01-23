package com.ayj.gradus.controllers;

import javax.validation.Valid;

import com.ayj.gradus.model.Note;
import com.ayj.gradus.services.CustomNoteService;
import com.ayj.gradus.utils.CommonUtils;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NoteController {

  @Autowired
  private CustomNoteService noteService;

  @Autowired
  private CommonUtils commonUtils;

  /**
   * Shows the notes page
   * 
   * @param modelAndView
   * @return modelAndView
   */
  @GetMapping("/notes")
  public ModelAndView notesList(ModelAndView modelAndView) {

    String currUserEmail;
    String date;

    currUserEmail = commonUtils.getUserEmail(modelAndView);
    date = commonUtils.getDate();

    modelAndView.addObject("username", currUserEmail);
    modelAndView.addObject("notes", noteService.getNotesByUser(currUserEmail));
    modelAndView.addObject("current_date", date);

    modelAndView.setViewName("pages/notes");

    return modelAndView;
  }

  /**
   * Shows the note creation page
   * 
   * @param modelAndView
   * @param note         - note object to be filled with form data
   * @return modelAndView
   */
  @GetMapping("/note/write")
  public ModelAndView addNote(ModelAndView modelAndView, Note note) {

    modelAndView.addObject("note", note);

    modelAndView.setViewName("pages/create_note");

    return modelAndView;
  }

  /**
   * Returns the interface for creating a new note, takes all the data entered in
   * and parses the markdown into html and then persisting it to the database
   * 
   * @param modelAndView
   * @param note          - note object with the filled in attributes
   * @param bindingResult - if we need to reject something
   * @return ModelAndView - a new model and view object containing a redirect to a
   *         new endpoint
   */
  @PostMapping("/note/write")
  public ModelAndView submitNote(ModelAndView modelAndView, @Valid Note note, BindingResult bindingResult) {

    String rawText;
    String currUserEmail;
    String finalDocument;
    String noteTitle;
    Parser mdParser;
    Node document;
    HtmlRenderer renderer;
    boolean isImportant;

    currUserEmail = commonUtils.getUserEmail(modelAndView);
    noteTitle = note.getNoteTitle();
    isImportant = note.getIsImportant();
    rawText = note.getNoteDescription().replaceAll("(\r\n|\n)", "<br/>");

    mdParser = Parser.builder().build();

    document = mdParser.parse(rawText);

    renderer = HtmlRenderer.builder().build();

    finalDocument = renderer.render(document);

    note.setAuthor(currUserEmail);
    note.setNoteTitle(noteTitle);
    note.setNoteDescription(finalDocument);
    note.setIsImportant(isImportant);

    noteService.saveNote(note);

    return new ModelAndView("redirect:/notes");
  }

  /**
   * GET method to delete a note using the id
   * 
   * @param id           - passed in the url, specifies what note to delete
   * @param modelAndView
   * @return ModelAndView - a new model and view object containing a redirect to a
   *         new endpoint
   */
  @GetMapping("/note/delete")
  public ModelAndView deleteNote(@RequestParam long id, ModelAndView modelAndView) {

    noteService.deleteNote(id);
    modelAndView.setViewName("pages/notes");

    return new ModelAndView("redirect:/notes");
  }

}

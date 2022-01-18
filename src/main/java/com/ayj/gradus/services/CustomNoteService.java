package com.ayj.gradus.services;

import java.util.List;
import java.util.Optional;

import com.ayj.gradus.model.Note;
import com.ayj.gradus.repositories.NoteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomNoteService implements NoteService {

  @Autowired
  private NoteRepository noteRepository;

  @Autowired
  public CustomNoteService(NoteRepository noteRepository) {
    this.noteRepository = noteRepository;
  }

  @Override
  public List<Note> getNotesByUser(String author) {
    return noteRepository.findByAuthor(author);
  }

  @Override
  public Optional<Note> getNoteById(long id) {
    return noteRepository.findById(id);
  }

  @Override
  public void createNote(String author, String noteTitle, String noteDescription, boolean isImportant) {
    noteRepository.save(new Note(author, noteTitle, noteDescription, isImportant));
  }

  @Override
  public void deleteNote(long id) {
    Optional<Note> nOptional = noteRepository.findById(id);
    if (nOptional.isPresent()) {
      noteRepository.delete(nOptional.get());
    }
  }

  @Override
  public void saveNote(Note note) {
    noteRepository.save(note);
  }

}

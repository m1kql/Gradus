package com.ayj.gradus.repositories;

import java.util.List;

import com.ayj.gradus.model.Note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

  List<Note> findByAuthor(String author);

}

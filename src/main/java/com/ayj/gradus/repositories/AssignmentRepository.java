package com.ayj.gradus.repositories;

import java.util.List;

import com.ayj.gradus.model.Assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

  List<Assignment> findByUsername(String username);

}

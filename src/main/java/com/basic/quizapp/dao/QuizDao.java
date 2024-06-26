package com.basic.quizapp.dao;

import com.basic.quizapp.model.Quiz;
import com.basic.quizapp.model.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuizDao extends JpaRepository<Quiz,Integer> {
}

package com.basic.quizapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.basic.quizapp.model.Questions;
import com.basic.quizapp.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;

    public ResponseEntity<List<Questions>> getAllQuestions() {
       try{
           return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
       }
       catch (Exception e){
           e.printStackTrace();
       }
       return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_GATEWAY);
    }

    public ResponseEntity<List<Questions>>  getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);


    }

    public ResponseEntity<String> addQuestion(Questions question) {
        try {
            // Assuming the question has an ID field that can be used to check its existence
            Optional<Questions> existingQuestion = questionDao.findById(question.getId());
            if (existingQuestion.isPresent()) {
                return new ResponseEntity<>("Question already present", HttpStatus.CONFLICT);
            }

            questionDao.save(question);
            return new ResponseEntity<>("Question added successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while adding the question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

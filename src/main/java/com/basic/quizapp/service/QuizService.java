package com.basic.quizapp.service;


import com.basic.quizapp.dao.QuestionDao;
import com.basic.quizapp.dao.QuizDao;
import com.basic.quizapp.model.QuestionWrapper;
import com.basic.quizapp.model.Questions;
import com.basic.quizapp.model.Quiz;
import com.basic.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Questions>questions=questionDao.findRandomQuestionsByCategory(category,numQ);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }


    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        try {
            Optional<Quiz> quiz=quizDao.findById(id);
            List<Questions> questionsFromDB=quiz.get().getQuestions();
            List<QuestionWrapper> questionForUser=new ArrayList<>();
            for(Questions q:questionsFromDB){
                QuestionWrapper qw=new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
                questionForUser.add(qw);
            }

            return new ResponseEntity<>(questionForUser, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
            Quiz quiz=quizDao.findById(id).get();
            int right=0,i=0;
            List<Questions> questions=quiz.getQuestions();
            for(Response r:responses){
                if(r.getResponse().equals(questions.get(i).getRightAnswer())){
                    right++;
                }
                i++;
            }
            return new ResponseEntity<>(right, HttpStatus.OK);
    }
}

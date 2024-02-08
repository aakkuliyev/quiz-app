package com.aakkuliyev.quizapp.service;

import com.aakkuliyev.quizapp.model.Question;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;

public interface IQuestionService {
    Question createQuestion(Question question);
    List<Question> getAllQuestions();
    Optional<Question> getQuestionById(Long id);
    List<String> getAllSubjects();
    Question updateQuestion(Long id, Question question) throws ChangeSetPersister.NotFoundException;
    void deleteQuestion(Long id);
    List<Question> getQuestionForUser(Integer numOfQuestion, String subject);
}

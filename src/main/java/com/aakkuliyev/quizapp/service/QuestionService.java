package com.aakkuliyev.quizapp.service;

import com.aakkuliyev.quizapp.model.Question;
import com.aakkuliyev.quizapp.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService{
    private final QuestionRepository questionRepository;

    @Override
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public List<String> getAllSubjects() {
        return questionRepository.findDistinctSubject();
    }

    @Override
    public Question updateQuestion(Long id, Question question) throws ChangeSetPersister.NotFoundException {
        Optional<Question> theQuestion = this.getQuestionById(id);
        if (theQuestion.isPresent()){
            Question updatedQuestion = theQuestion.get();
            updatedQuestion.setQuestion(question.getQuestion());
            updatedQuestion.setChoices(question.getChoices());
            updatedQuestion.setCorrectAnswers(question.getCorrectAnswers());
            return questionRepository.save(updatedQuestion);
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    } //найдет вопрос по Id. Если он существует, создает измененный вопрос и получает все данные. В измененном вопросе,
    // можно изменить по пунктам вопрос, правильный ответ и выборы.  После всего, он сохраняет измененный вопрос как основной

    @Override
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    public List<Question> getQuestionForUser(Integer numOfQuestion, String subject) {
        Pageable pageable = PageRequest.of(0, numOfQuestion);
        return questionRepository.findBySubject(subject, pageable).getContent();
    }
}

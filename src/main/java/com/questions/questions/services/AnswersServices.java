package com.questions.questions.services;

import com.questions.questions.dao.Answers;
import com.questions.questions.dao.Questions;

import java.util.List;

public interface AnswersServices {

    void createUsersQuestions(List<Answers> answers);
    List<Answers> findByQuestion(Long id);
}

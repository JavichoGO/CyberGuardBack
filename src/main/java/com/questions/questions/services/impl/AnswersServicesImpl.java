package com.questions.questions.services.impl;

import com.questions.questions.dao.Answers;
import com.questions.questions.dao.OptionsHead;
import com.questions.questions.dao.Questions;
import com.questions.questions.repository.AnswersRepository;
import com.questions.questions.repository.OptionsHeadRepository;
import com.questions.questions.repository.OptionsRepository;
import com.questions.questions.services.AnswersServices;
import com.questions.questions.services.OptionsHeadServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswersServicesImpl implements AnswersServices {

    @Autowired
    private AnswersRepository answersRepository;

    @Override
    public void createUsersQuestions(List<Answers> answers) {
        this.answersRepository.saveAll(answers);
    }

    @Override
    public List<Answers> findByQuestion(Long id) {
        return this.answersRepository.findByQuestion(id);
    }
}

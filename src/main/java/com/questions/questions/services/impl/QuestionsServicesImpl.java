package com.questions.questions.services.impl;

import com.questions.questions.dao.OptionsHead;
import com.questions.questions.dao.Questions;
import com.questions.questions.repository.OptionsHeadRepository;
import com.questions.questions.repository.OptionsRepository;
import com.questions.questions.repository.QuestionsRepository;
import com.questions.questions.services.OptionsHeadServices;
import com.questions.questions.services.QuestionsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionsServicesImpl implements QuestionsServices {

    @Autowired
    private QuestionsRepository questionsRepository;


    @Override
    public void createOptionsHead(Questions questions) {
        this.questionsRepository.save(questions);
    }

    @Override
    public Questions findById(Long id) {
        return this.questionsRepository.findById(id).orElse(null);
    }

    @Override
    public List<Questions> findAll() {
        return (List<Questions>) this.questionsRepository.findAll();
    }

    @Override
    public List<Questions> findByActive(Boolean bo) {
        return this.questionsRepository.findByActive(bo);
    }
}

package com.questions.questions.services;

import com.questions.questions.dao.Options;
import com.questions.questions.dao.Questions;

import java.util.List;

public interface QuestionsServices {

    void createOptionsHead(Questions questions);

    Questions findById(Long id);

    List<Questions> findAll();
    List<Questions> findByActive(Boolean bo);
}

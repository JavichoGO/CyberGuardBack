package com.questions.questions.services;


import com.questions.questions.dao.OptionsHead;

public interface OptionsHeadServices {

    void createOptionsHead(OptionsHead optionsHead);

    OptionsHead findById(Long id);
}

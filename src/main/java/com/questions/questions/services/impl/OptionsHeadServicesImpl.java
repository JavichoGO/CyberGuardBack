package com.questions.questions.services.impl;

import com.questions.questions.dao.OptionsHead;
import com.questions.questions.repository.OptionsHeadRepository;
import com.questions.questions.repository.OptionsRepository;
import com.questions.questions.services.OptionsHeadServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionsHeadServicesImpl implements OptionsHeadServices {

    @Autowired
    private OptionsRepository optionsRepository;

    @Autowired
    private OptionsHeadRepository optionsHeadRepository;


    @Override
    public void createOptionsHead(OptionsHead optionsHead) {
        optionsHeadRepository.save(optionsHead);
    }

    @Override
    public OptionsHead findById(Long id) {
        return this.optionsHeadRepository.findById(id).orElse(null);
    }
}

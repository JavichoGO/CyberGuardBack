package com.questions.questions.services.impl;

import com.questions.questions.dao.Options;
import com.questions.questions.dao.OptionsHead;
import com.questions.questions.repository.OptionsHeadRepository;
import com.questions.questions.repository.OptionsRepository;
import com.questions.questions.services.OptionsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OptionsServicesImpl implements OptionsServices {

    @Autowired
    private OptionsRepository optionsRepository;

    @Autowired
    private OptionsHeadRepository optionsHeadRepository;


    @Override
    public List<Options> createOptionsHead(List<Options> options) {
        return (List<Options>) optionsRepository.saveAll(options);
    }

    @Override
    public void createOptions(Options options) {
        this.optionsRepository.save(options);
    }

    @Override
    public List<Options> findByOptionsHead(OptionsHead optionsHead) {
        return this.optionsRepository.findByOptionsHead(optionsHead);
    }

    @Override
    public Optional<Options> findById(Long id) {
        return this.optionsRepository.findById(id);
    }
}

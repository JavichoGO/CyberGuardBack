package com.questions.questions.services;

import com.questions.questions.dao.Options;
import com.questions.questions.dao.OptionsHead;

import java.util.List;
import java.util.Optional;

public interface OptionsServices {

    List<Options> createOptionsHead(List<Options> options);
    void createOptions(Options options);
    List<Options> findByOptionsHead(OptionsHead optionsHead);
    Optional<Options> findById(Long id);
}

package com.questions.questions.repository;

import com.questions.questions.dao.Options;
import com.questions.questions.dao.OptionsHead;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionsRepository extends CrudRepository<Options, Long> {

    List<Options> findByOptionsHead(OptionsHead optionsHead);
}

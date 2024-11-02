package com.questions.questions.repository;

import com.questions.questions.dao.Options;
import com.questions.questions.dao.OptionsHead;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionsHeadRepository extends CrudRepository<OptionsHead, Long> {
}

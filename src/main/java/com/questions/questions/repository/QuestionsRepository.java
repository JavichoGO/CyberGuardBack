package com.questions.questions.repository;

import com.questions.questions.dao.Answers;
import com.questions.questions.dao.Questions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsRepository extends CrudRepository<Questions, Long> {
    List<Questions> findByActive(Boolean bo);
}

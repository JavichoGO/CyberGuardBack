package com.questions.questions.repository;

import com.questions.questions.dao.Answers;
import com.questions.questions.dao.Questions;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswersRepository extends CrudRepository<Answers, Long> {

    @Query("SELECT an FROM Answers an WHERE an.userQuestions.questions.id = :questions")
    List<Answers> findByQuestion(@Param("questions") Long id);
}

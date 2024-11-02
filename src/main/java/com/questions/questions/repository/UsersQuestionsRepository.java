package com.questions.questions.repository;

import com.questions.questions.dao.UserQuestions;
import com.questions.questions.dao.UserQuestionsHead;
import com.questions.questions.dao.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersQuestionsRepository extends CrudRepository<UserQuestions, Long> {

    List<UserQuestions> findByUserQuestionsHead(UserQuestionsHead userQuestionsHead);
}

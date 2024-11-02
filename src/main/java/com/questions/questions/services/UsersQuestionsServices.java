package com.questions.questions.services;

import com.questions.questions.dao.Questions;
import com.questions.questions.dao.UserQuestions;
import com.questions.questions.dao.UserQuestionsHead;
import com.questions.questions.dao.Users;

import java.util.List;

public interface UsersQuestionsServices {

    void createUsersQuestions(List<UserQuestions> userQuestions);
    void createUsersQuestions(UserQuestions userQuestions);
    UserQuestions findByUserQuestions(Long id);
    List<UserQuestions> findByUserQuestionsHead(UserQuestionsHead userQuestionsHead);
}

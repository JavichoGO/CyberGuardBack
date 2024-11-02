package com.questions.questions.services;

import com.questions.questions.dao.UserQuestionsHead;
import com.questions.questions.dao.Users;

import java.util.List;

public interface UsersQuestionsHeadServices {

    void createUsersQuestionsHead(UserQuestionsHead userQuestionsHead);
    void createUsersQuestionsHead(List<UserQuestionsHead> userQuestionsHead);
    UserQuestionsHead findByUser(Long id);
    UserQuestionsHead findByUserLastVersion(Long id);
    List<UserQuestionsHead> findByUser(Users users);
}

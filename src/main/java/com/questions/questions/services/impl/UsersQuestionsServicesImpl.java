package com.questions.questions.services.impl;

import com.questions.questions.dao.Questions;
import com.questions.questions.dao.UserQuestions;
import com.questions.questions.dao.UserQuestionsHead;
import com.questions.questions.dao.Users;
import com.questions.questions.repository.QuestionsRepository;
import com.questions.questions.repository.UsersQuestionsRepository;
import com.questions.questions.services.QuestionsServices;
import com.questions.questions.services.UsersQuestionsServices;
import jakarta.persistence.Transient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersQuestionsServicesImpl implements UsersQuestionsServices {

    @Autowired
    private UsersQuestionsRepository usersQuestionsRepository;

    @Override
    public void createUsersQuestions(List<UserQuestions> userQuestions) {
        this.usersQuestionsRepository.saveAll(userQuestions);
    }

    @Override
    public void createUsersQuestions(UserQuestions userQuestions) {
        this.usersQuestionsRepository.save(userQuestions);
    }

    @Override
    public UserQuestions findByUserQuestions(Long id) {
        return usersQuestionsRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserQuestions> findByUserQuestionsHead(UserQuestionsHead userQuestionsHead) {
        return this.usersQuestionsRepository.findByUserQuestionsHead(userQuestionsHead);
    }

}

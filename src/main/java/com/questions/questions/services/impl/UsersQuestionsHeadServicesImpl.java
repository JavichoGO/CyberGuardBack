package com.questions.questions.services.impl;

import com.questions.questions.dao.UserQuestionsHead;
import com.questions.questions.dao.Users;
import com.questions.questions.repository.UsersQuestionsHeadRepository;
import com.questions.questions.repository.UsersRepository;
import com.questions.questions.services.UsersQuestionsHeadServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersQuestionsHeadServicesImpl implements UsersQuestionsHeadServices {

    @Autowired
    private UsersQuestionsHeadRepository usersQuestionsHeadRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public void createUsersQuestionsHead(UserQuestionsHead userQuestionsHead) {
        this.usersQuestionsHeadRepository.save(userQuestionsHead);
    }

    @Override
    public void createUsersQuestionsHead(List<UserQuestionsHead> userQuestionsHead) {
        this.usersQuestionsHeadRepository.saveAll(userQuestionsHead);
    }

    @Override
    public UserQuestionsHead findByUser(Long id) {
        return this.usersQuestionsHeadRepository.findByUsersList(id);
    }

    @Override
    public UserQuestionsHead findByUserLastVersion(Long id) {
        return this.usersQuestionsHeadRepository.findByUsersLastVersion(id);
    }

    @Override
    public List<UserQuestionsHead> findByUser(Users users) {
        return this.usersQuestionsHeadRepository.findByUser(users);
    }
}

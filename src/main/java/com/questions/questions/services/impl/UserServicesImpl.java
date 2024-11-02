package com.questions.questions.services.impl;

import com.questions.questions.dao.Users;
import com.questions.questions.dto.GraphicsView;
import com.questions.questions.repository.UsersRepository;
import com.questions.questions.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Users findById(Long id) {
        return this.usersRepository.findById(id).orElse(null);
    }

    @Override
    public void create(Users users) {
        this.usersRepository.save(users);
    }

    @Override
    public List<Users> findByAll() {
       return (List<Users>) this.usersRepository.findAll();
    }

    @Override
    public void update(Users users) {
        this.usersRepository.save(users);
    }

    @Override
    public Users findByEmail(String email) {
        return this.usersRepository.findByEmail(email) ;
    }

    @Override
    public Users findByIdentification(String identification) {
        return this.usersRepository.findByIdentification(identification);
    }

    @Override
    public List<GraphicsView> graphicsCategory() {
        return this.usersRepository.graphicsCategory();
    }

    @Override
    public List<GraphicsView> graphicsFunction() {
        return this.usersRepository.graphicsFunction();
    }

    @Override
    public List<GraphicsView> graphicsFunctionIdentification(String identification) {
        return this.usersRepository.graphicsFunctionIdentification(identification);
    }
}

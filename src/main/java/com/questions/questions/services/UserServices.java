package com.questions.questions.services;

import com.questions.questions.dao.Users;
import com.questions.questions.dto.GraphicsView;

import java.util.List;

public interface UserServices {

    Users findById(Long id);
    void create(Users users);
    List<Users> findByAll();
    void update(Users users);
    Users findByEmail(String email);
    Users findByIdentification(String identification);
    List<GraphicsView> graphicsCategory();
    List<GraphicsView> graphicsFunction();
    List<GraphicsView> graphicsFunctionIdentification(String identification);
}

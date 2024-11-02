package com.questions.questions.repository;

import com.questions.questions.dao.Answers;
import com.questions.questions.dao.Roles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends CrudRepository<Roles, Long> {

    Roles findByName(String name);
}

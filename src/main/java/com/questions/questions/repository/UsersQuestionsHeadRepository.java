package com.questions.questions.repository;

import com.questions.questions.dao.UserQuestionsHead;
import com.questions.questions.dao.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersQuestionsHeadRepository extends CrudRepository<UserQuestionsHead, Long> {

    @Query("select uq from UserQuestionsHead uq where uq.user.id = ?1 AND uq.finished = false")
    UserQuestionsHead findByUsersList(Long id);

    List<UserQuestionsHead> findByUser(Users Users);

    @Query("select uq from UserQuestionsHead uq where uq.user.id = ?1 ORDER BY uq.version DESC LIMIT 1 ")
    UserQuestionsHead findByUsersLastVersion(Long id);
}

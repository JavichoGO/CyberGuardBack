package com.questions.questions.repository;

import com.questions.questions.dao.Users;
import com.questions.questions.dto.GraphicsView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.questions.questions.util.UtilityConstant.*;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long> {
    Users findByEmail(String email);

    Users findByIdentification(String identification);

    @Query(QUERY_GRAPHICS)
    List<GraphicsView> graphicsCategory();

    @Query(QUERY_GRAPHICS_FUNCTION)
    List<GraphicsView> graphicsFunction();

    @Query(QUERY_GRAPHICS_FUNCTION_IDENTIFICATION)
    List<GraphicsView> graphicsFunctionIdentification(@Param("identification") String identification);
}

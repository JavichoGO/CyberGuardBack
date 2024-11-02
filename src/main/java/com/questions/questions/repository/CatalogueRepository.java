package com.questions.questions.repository;

import com.questions.questions.dao.Catalogues;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogueRepository extends CrudRepository<Catalogues, Long> {

    List<Catalogues> findByCatalogueType(Integer integer);
}

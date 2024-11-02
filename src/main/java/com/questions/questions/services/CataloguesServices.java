package com.questions.questions.services;

import com.questions.questions.dao.Catalogues;

import java.util.List;

public interface CataloguesServices {

    List<Catalogues> findByAll();
    List<Catalogues> findByCatalogueType(Integer integer);
}

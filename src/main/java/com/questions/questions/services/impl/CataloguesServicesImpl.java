package com.questions.questions.services.impl;

import com.questions.questions.dao.Catalogues;
import com.questions.questions.repository.CatalogueRepository;
import com.questions.questions.services.CataloguesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CataloguesServicesImpl implements CataloguesServices {

    @Autowired
    private CatalogueRepository catalogueRepository;

    @Override
    public List<Catalogues> findByAll() {
        return (List<Catalogues>) this.catalogueRepository.findAll();
    }

    @Override
    public List<Catalogues> findByCatalogueType(Integer integer) {
        return this.catalogueRepository.findByCatalogueType(integer);
    }
}

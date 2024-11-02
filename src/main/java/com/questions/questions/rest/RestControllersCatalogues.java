package com.questions.questions.rest;

import com.questions.questions.operator.CataloguesOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/catalogues")
public class RestControllersCatalogues {

    @Autowired
    private CataloguesOperator cataloguesOperator;

    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listCatalogues() {
        return this.cataloguesOperator.findAllCatalogues();
    }
}

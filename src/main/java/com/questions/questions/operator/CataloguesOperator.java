package com.questions.questions.operator;

import com.questions.questions.dao.Catalogues;
import com.questions.questions.services.impl.CataloguesServicesImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Log4j2
public class CataloguesOperator {

    @Autowired
    private CataloguesServicesImpl cataloguesServicesImpl;

    public ResponseEntity<?> findAllCatalogues() {
        List<Catalogues> cataloguesList = this.cataloguesServicesImpl.findByAll();
        Map<String, List<Catalogues>> cataloguesMap = cataloguesList.stream()
                .collect(Collectors.groupingBy(catalogue -> {
                    if (catalogue.getCatalogueType() == 1) {
                        return "funcion";
                    } else if (catalogue.getCatalogueType() == 2) {
                        return "categoria";
                    } else {
                        return "otro"; // En caso de que haya otros valores
                    }
                }));

        return new ResponseEntity<>(cataloguesMap, HttpStatus.CREATED);
    }
}

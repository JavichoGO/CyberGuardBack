package com.questions.questions.rest;

import com.questions.questions.dto.OptionsCreateDto;
import com.questions.questions.operator.OptionsOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControllersOptions {

    @Autowired
    private OptionsOperator optionsOperator;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/create/options", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createOptions(@RequestBody OptionsCreateDto optionsCreateDto) {
        return optionsOperator.createOptions(optionsCreateDto);
    }
}

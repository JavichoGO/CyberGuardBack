package com.questions.questions.util;

import com.questions.questions.dao.Catalogues;
import com.questions.questions.dao.Questions;
import com.questions.questions.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public class UtilityMethods {
    public static ResponseEntity<?> responseEntity(String message, HttpStatus httpStatus){
        return new ResponseEntity<>(Map.of("message", message),httpStatus);
    }

    public static ResponseEntity<?> responseEntityDto(String message, Object object, HttpStatus httpStatus) {
        return new ResponseEntity<>(ResponseDto.builder()
                .data(object)
                .message(message)
                .build(),
                httpStatus);
    }

    public static String findDescriptionCatalogue(List<Catalogues> catalogues, Integer integer) {
        return catalogues.stream().filter(ca -> ca.getId().equals(integer.longValue()))
                .findAny()
                .map(Catalogues::getDescription)
                .orElse("");
    }
}

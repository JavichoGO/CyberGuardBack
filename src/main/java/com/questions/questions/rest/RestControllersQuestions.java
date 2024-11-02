package com.questions.questions.rest;

import com.questions.questions.dto.DeleteQuestionRequestDto;
import com.questions.questions.dto.OptionsCreateDto;
import com.questions.questions.dto.QuestionsCreateRequestDto;
import com.questions.questions.dto.QuestionsUpdateRequestDto;
import com.questions.questions.operator.OptionsOperator;
import com.questions.questions.operator.QuestionsOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/question")
public class RestControllersQuestions {

    @Autowired
    private QuestionsOperator questionsOperator;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createQuestions(@RequestBody QuestionsCreateRequestDto questionsCreateRequestDto) {
        return this.questionsOperator.createQuestions(questionsCreateRequestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listQuestions() {
        return this.questionsOperator.listQuestions();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteQuestion(@RequestBody DeleteQuestionRequestDto deleteQuestionRequestDto) {
        return this.questionsOperator.deleteQuestion(deleteQuestionRequestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateQuestion(@RequestBody QuestionsUpdateRequestDto questionsCreateRequestDto) {
        return this.questionsOperator.updateQuestion(questionsCreateRequestDto);
    }
}

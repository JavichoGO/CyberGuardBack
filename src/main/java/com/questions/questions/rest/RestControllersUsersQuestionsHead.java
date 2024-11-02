package com.questions.questions.rest;

import com.questions.questions.dto.QuestionRequestDto;
import com.questions.questions.dto.UsersQuestionsCreateRequestDto;
import com.questions.questions.dto.UsersRequestDto;
import com.questions.questions.operator.UsersQuestionsHeadOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/question-user")
public class RestControllersUsersQuestionsHead {

    @Autowired
    private UsersQuestionsHeadOperator usersQuestionsHeadOperator;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping(path = "/find", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findQuestions(@RequestBody UsersRequestDto usersRequestDto) {
        return this.usersQuestionsHeadOperator.listQuestionUser(usersRequestDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping(path = "/save/answers", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveAnswers(@RequestBody QuestionRequestDto questionRequestDto) {
        return this.usersQuestionsHeadOperator.saveResponse(questionRequestDto);
    }
}

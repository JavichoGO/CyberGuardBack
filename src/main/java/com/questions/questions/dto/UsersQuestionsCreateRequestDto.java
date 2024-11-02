package com.questions.questions.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsersQuestionsCreateRequestDto {

    private List<Long> userQuestionsList;
    private Long user;

}

package com.questions.questions.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UsersListResponseDto {
    private Long id;
    private String nameAll;
    private String identification;
    private String password;
    private String position;
    private String dateOfBirth;
    private String email;
    private boolean surveyAnswered;
}

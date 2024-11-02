package com.questions.questions.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateNewPasswordRecoveryRequestDto {
    private String identification;
    private Integer token;
    private String password;
    private String passwordTwo;
}

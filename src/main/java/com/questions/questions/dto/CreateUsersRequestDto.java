package com.questions.questions.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUsersRequestDto {
    private String nameAll;
    private String identification;
    private String position;
    private String dateOfBirth;
    private String email;
    private String password;
}

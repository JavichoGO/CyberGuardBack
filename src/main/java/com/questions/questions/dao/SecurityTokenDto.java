package com.questions.questions.dao;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityTokenDto {
    private String token;
    private String fullName;
}

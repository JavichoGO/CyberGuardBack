package com.questions.questions.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionRequestDto {
    private List<Responses> responses;

    @Getter
    @Setter
    public static class Responses {
        private Long questionId;
        private Integer optionValue;
    }
}

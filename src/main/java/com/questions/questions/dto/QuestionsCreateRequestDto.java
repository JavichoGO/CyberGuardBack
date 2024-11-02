package com.questions.questions.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QuestionsCreateRequestDto {
    private String nameQuestions;
    private Integer functionQuestions;
    private Integer categoryQuestions;
    private List<OptionValue> options = new ArrayList<>();

    @Getter
    @Setter
    public static class OptionValue {
        private String optionName;
        private Integer optionValue;
    }
}

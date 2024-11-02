package com.questions.questions.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QuestionsUpdateRequestDto {
    private Long idQuestion;
    private String nameQuestions;
    private Integer functionQuestions;
    private Integer categoryQuestions;
    private List<OptionValue> options = new ArrayList<>();

    @Getter
    @Setter
    public static class OptionValue {
        private Long idOption;
        private String optionName;
        private Integer optionValue;
    }
}

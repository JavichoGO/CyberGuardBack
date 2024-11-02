package com.questions.questions.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.questions.questions.dao.Options;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class UserQuestionResponseDto {

    private boolean finish;
    private Map<String, List<Question>> questions;

    @Getter
    @Setter
    public static class Question {
        private Long idQuestion;
        private Integer functionQuestions;
        private String functionQuestionsDescription;
        private Integer categoryQuestions;
        private String categoryQuestionsDescription;
        private Boolean replied;
        private String nameQuestion;
        @JsonIgnoreProperties(value = "optionsHead")
        private List<Options> optionsList;

    }
}

package com.questions.questions.dto;

import com.questions.questions.dao.Options;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class QuestionListResponseDto {
    private Long idQuestion;
    private String nameQuestions;
    private Integer functionQuestions;
    private String functionQuestionsDescription;
    private Integer categoryQuestions;
    private String categoryQuestionsDescription;
    private Integer weight;
    private boolean replied;
    private List<Options> optionsList;

    @Builder
    @Setter
    @Getter
    public static class Options {
        private Long idOption;
        private String optionName;
        private Integer optionValue;
    }
}

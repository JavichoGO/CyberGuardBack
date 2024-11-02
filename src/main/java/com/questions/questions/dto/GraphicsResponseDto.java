package com.questions.questions.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GraphicsResponseDto {
    public List<Data> category = new ArrayList<>();
    public List<Data> function = new ArrayList<>();

    @Getter
    @Setter
    public static class Data {
        private Long idCategory;
        private String categoryDescription;
        private Long idFunction;
        private String functionDescription;
        private Double average;
        private Integer desired;
    }
}

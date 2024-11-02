package com.questions.questions.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OptionsCreateDto {

    private List<OptionsList> optionsList;

    @Getter
    @Setter
    public static class OptionsList{
        private String nameOption;
    }
}

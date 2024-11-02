package com.questions.questions.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "t_options_head")
public class OptionsHead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @OneToMany(mappedBy = "optionsHead", fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties(value = "optionsHead")
    private List<Options> optionsList;
}

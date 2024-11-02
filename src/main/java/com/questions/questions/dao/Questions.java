package com.questions.questions.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "t_questions")
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String nameQuestions;

    @Column(name = "function_question", nullable = false)
    private Integer functionQuestions;

    @Column(name = "category_question", nullable = false)
    private Integer categoryQuestions;

    @ManyToOne
    @JoinColumn(name = "options_head", nullable = false)
    private OptionsHead optionsHead;

    @Column(name = "question_active")
    private Boolean active;

}

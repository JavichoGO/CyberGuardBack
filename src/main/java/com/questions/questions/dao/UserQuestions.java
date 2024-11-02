package com.questions.questions.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "t_user_questions")
public class UserQuestions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Questions questions;

    @ManyToOne
    @JoinColumn(name = "user_question_head_id")
    private UserQuestionsHead userQuestionsHead;

    private Boolean replied;
}

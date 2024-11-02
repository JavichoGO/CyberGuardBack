package com.questions.questions.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_user_questions_head")
public class UserQuestionsHead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Users user;

    @Column(nullable = false)
    private Boolean finished;

    private Integer version;

    @OneToMany(mappedBy = "userQuestionsHead")
    @JsonIgnoreProperties(value = "userQuestionsHead")
    private List<UserQuestions> userQuestionsList;
}

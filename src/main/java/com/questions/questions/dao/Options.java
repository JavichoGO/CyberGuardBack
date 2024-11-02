package com.questions.questions.dao;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "t_options")
public class Options {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_option", nullable = false, length = 250)
    private String nameOption;

    @Column(nullable = false)
    private Integer value;

    @JoinColumn(name = "options_head", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private OptionsHead optionsHead;
}

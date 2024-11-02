package com.questions.questions.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "t_catalogues")
//@Table(name = "t_catalogues")
public class Catalogues {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(name = "catalogue_type", nullable = false)
    private Integer catalogueType;

    @Column(name = "desired")
    private Integer desired;

    private Integer parent;
}


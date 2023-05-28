package com.digital_guru.hackathonbackend.entity.question;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * by Danil Koltovskikh at 26.03.2023
 */

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "question", schema = "schooldb")
public class Question {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "question", nullable = true)
    private String question;
    @Basic
    @Column(name = "correct_id", nullable = true)
    private Integer correctId;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "difficulty")
    private Difficulty difficulty;
    @Basic
    @Column(name = "description", nullable = true)
    private String description;
    @Column(name = "1_answers", nullable = true)
    private String firstAnswers;
    @Column(name = "2_answers", nullable = true)
    private String secondAnswers;
    @Column(name = "3_answers", nullable = true)
    private String thirdAnswers;
    @Column(name = "4_answers", nullable = true)
    private String fourthAnswers;
}
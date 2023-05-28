package com.digital_guru.hackathonbackend.dto;

import com.digital_guru.hackathonbackend.entity.question.Difficulty;
import lombok.Data;

@Data
public class QuestionDto {
    private Long id;
    private String question;
    private Integer correctId;
    private String description;
    private Difficulty difficulty;
    private String firstAnswers;
    private String secondAnswers;
    private String thirdAnswers;
    private String fourthAnswers;

}

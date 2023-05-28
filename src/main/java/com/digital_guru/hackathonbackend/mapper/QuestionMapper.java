package com.digital_guru.hackathonbackend.mapper;

import com.digital_guru.hackathonbackend.dto.QuestionDto;
import com.digital_guru.hackathonbackend.entity.question.Question;

/**
 * by Danil Koltovskikh at 26.03.2023
 */

public class QuestionMapper<T extends QuestionDto, R extends Question> {

    public Question map(T oldQuestion, R newQuestion) {
        newQuestion.setQuestion(oldQuestion.getQuestion());
        newQuestion.setDifficulty(oldQuestion.getDifficulty());
        newQuestion.setCorrectId(oldQuestion.getCorrectId());
        newQuestion.setDescription(oldQuestion.getDescription());
        newQuestion.setFirstAnswers(oldQuestion.getFirstAnswers());
        newQuestion.setSecondAnswers(oldQuestion.getSecondAnswers());
        newQuestion.setThirdAnswers(oldQuestion.getThirdAnswers());
        newQuestion.setFourthAnswers(oldQuestion.getFourthAnswers());
        return newQuestion;
    }
}

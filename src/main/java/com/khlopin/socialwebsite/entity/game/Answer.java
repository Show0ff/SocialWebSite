package com.khlopin.socialwebsite.entity.game;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer extends GameEntity {

    private Long answerId;
    private String text;
    private Long questionId;

    private AnswerState answerState;
    private String finishAnswerText;

    public Answer(Long answerId, String text, Long questionId, AnswerState answerState) {
        this.answerId = answerId;
        this.text = text;
        this.questionId = questionId;
        this.answerState = answerState;
    }
}

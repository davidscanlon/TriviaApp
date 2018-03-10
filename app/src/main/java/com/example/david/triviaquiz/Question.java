package com.example.david.triviaquiz;

import java.util.ArrayList;

/**
 * Created by David on 3/4/18.
 */

public class Question {

    ArrayList<String> answerChoices;
    String id;
    String text;
    //TODO: figure out how to get the image
    String answer; // should this be int?

    public Question () {

    }


    public void addAnswerChoice(String choice) {
        answerChoices.add(choice);
    }

    public ArrayList<String> getAnswerChoices() {
        return answerChoices;
    }

    public void setAnswerChoices(ArrayList<String> answerChoices) {
        this.answerChoices = answerChoices;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

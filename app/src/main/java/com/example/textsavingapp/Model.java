package com.example.textsavingapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Model implements Serializable {

    private ArrayList<String> questions;
    private ArrayList<String> answers;
    private int currentIndex;

    // Constructor
    public Model(){
        questions = new ArrayList<String>();
        answers = new ArrayList<String>();

        questions.add("Why did you wake up today?");
        answers.add("");
        questions.add("Why are you alive?");
        answers.add("");
        currentIndex = 0;

    }

    // Getter/Setters

    void addQuestion(String question) {

        questions.add(question);
    }

    String recordAnswerAndGetNext(String answer) {
        if (currentIndex < answers.size()) {
            answers.set(currentIndex, answer);
        } else {
            answers.add(answer);
        }
        if (currentIndex < questions.size()-1) {
            currentIndex = currentIndex + 1;
        }
        return questions.get(currentIndex);
    }

    String getFirstQuestion() {
        return questions.get(0);
    }

    String getPrevQuestion() {
        if (questions.size() == 0) {
            return null;
        }
        if (currentIndex > 0) {
            currentIndex = currentIndex - 1;
        }
        return questions.get(currentIndex);
    }

    String getCurrentAnswer() {
        return answers.get(currentIndex);
    }

}

package com.askattest.interview.models;

public record Response(int respondent, int question, int choice) {
    public boolean fromRespondent(int respondentId) {
        return respondent == respondentId;
    }

    @Override
    public String toString() {
        return String.format("Respondent: %d chose option id: %d for Question: %d", respondent, choice, question);
    }
}

package com.askattest.interview.models;

public record Choice(String text, int route, int id) {
    public boolean isEndOfSurvey() {
        return route == -1;
    }
}

package com.askattest.interview.models;

import java.util.List;
import java.util.Optional;

public record Question(int id, String text, List<Choice> options, int payout) {
    public Optional<Integer> getRouteForChoice(int choiceId) {
        return options.stream()
                .filter(choice -> choice.id() == choiceId)
                .map(choice -> choice.route())
                .filter(route -> route > 0)
                .findFirst();
    }
}

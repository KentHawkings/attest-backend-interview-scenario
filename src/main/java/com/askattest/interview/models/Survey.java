package com.askattest.interview.models;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public record Survey(int id, String name, List<Question> questions) {
    @Override
    public String toString() {
        return String.format("Survey ID: %d | Name: %s | Question Count: %d", id, name, questions.size());
    }

    public Set<Integer> getQuestionIds() {
        return questions.stream().map(Question::id).collect(Collectors.toSet());
    }

    public Map<Integer, Integer> getQuestionPayouts() {
        return questions.stream().collect(Collectors.toMap(Question::id, Question::payout));
    }

    public Optional<Integer> getNextQuestionId(int currentQuestionId, int choiceId) {
        return getQuestionById(currentQuestionId)
                .flatMap(question -> question.getRouteForChoice(choiceId));
    }

    private Optional<Question> getQuestionById(int questionId) {
        return Optional.ofNullable(questionMap().get(questionId));
    }

    private Map<Integer, Question> questionMap() {
        return questions.stream().collect(Collectors.toMap(Question::id, q -> q));
    }
}

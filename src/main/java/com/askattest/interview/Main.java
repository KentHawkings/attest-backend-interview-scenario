package com.askattest.interview;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.askattest.interview.models.Survey;
import com.askattest.interview.repository.SurveyRepo;
import com.askattest.interview.service.DefaultResponseService;
import com.askattest.interview.service.DefaultSurveyService;

public class Main {
    public static void main(String[] args) {
        SurveyRepo surveyManager;
        try {
            surveyManager = new SurveyRepo(
                new DefaultSurveyService("survey.json"),
                new DefaultResponseService("responses.json")
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        var survey = surveyManager.surveyById(200);

        if (survey.isPresent()) {
            taskOne(survey.get(), surveyManager);
            taskTwo(survey.get(), surveyManager);
        } else {
            Logger.getGlobal().log(Level.WARNING, "Survey Not Found.");
        }
    }

    private static void taskOne(Survey survey, SurveyRepo surveyManager) {
        var respondentCounts = surveyManager.getQuestionsAnsweredPerRespondent(survey);

        System.out.printf("=== Task 1: Questions answered per respondent for Survey %d ===%n", survey.id());
        respondentCounts.forEach((respondentId, questionCount) ->
            System.out.printf("Respondent %d has answered %d questions%n", respondentId, questionCount)
        );
    }

    private static void taskTwo(Survey survey, SurveyRepo surveyManager) {
        var respondentPayouts = surveyManager.calculatePayoutsForSurvey(survey);

        System.out.printf("=== Task 2: Payout per respondent for Survey %d ===%n", survey.id());
        respondentPayouts.forEach((respondentId, payout) ->
            System.out.printf("Respondent %d has a payout of %d pence%n", respondentId, payout)
        );
    }
}
package com.askattest.interview.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.askattest.interview.models.Response;
import com.askattest.interview.models.Survey;
import com.askattest.interview.service.ResponseService;
import com.askattest.interview.service.SurveyService;

public class SurveyRepo {
    private final SurveyService surveyService;
    private final ResponseService responseService;

    private List<Survey> surveys;
    private List<Response> responses;

    public SurveyRepo(SurveyService surveyService, ResponseService responseService) {
        this.surveyService = surveyService;
        this.responseService = responseService;
    }

    public Optional<Survey> surveyById(int surveyId) {
        return getSurveys().stream()
                .filter(survey -> survey.id() == surveyId)
                .findFirst();
    }

    public Map<Integer, Integer> calculatePayoutsForSurvey(Survey survey) {
        Map<Integer, Integer> questionPayouts = survey.getQuestionPayouts();

        return getResponses().stream()
                .collect(Collectors.groupingBy(
                        Response::respondent,
                        Collectors.summingInt(response -> questionPayouts.getOrDefault(response.question(), 0))));
    }

    public Map<Integer, Long> getQuestionsAnsweredPerRespondent(Survey survey) {
        return getSurveyResponses(survey).collect(Collectors.groupingBy(Response::respondent, Collectors.counting()));
    }

    private Stream<Response> getSurveyResponses(Survey survey) {
        var questionIds = survey.getQuestionIds();
        return getResponses().stream().filter(response -> questionIds.contains(response.question()));
    }

    private List<Survey> getSurveys() {
        if (surveys == null) {
            surveys = surveyService.listSurveys();
        }
        return surveys;
    }

    private List<Response> getResponses() {
        if (responses == null) {
            responses = responseService.listResponses();
        }
        return responses;
    }
}

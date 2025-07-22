package com.askattest.interview.service;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.askattest.interview.models.Survey;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DefaultSurveyService implements SurveyService {
    private final String filename;

    public DefaultSurveyService(String filename) {
        this.filename = filename;
    }

    @Override
    public List<Survey> listSurveys() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            URL filenameUrl = DefaultSurveyService.class.getResource("/survey-data/" + this.filename);
            if (filenameUrl == null) {
                throw new IOException("Resource not found: " + this.filename);
            }
            return List.of(objectMapper.readValue(filenameUrl, Survey.class));
        } catch (IOException e) {
            throw new RuntimeException("Could not load survey from file: " + this.filename, e);
        }
    }
}

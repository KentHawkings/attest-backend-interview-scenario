package com.askattest.interview.service;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import com.askattest.interview.models.Response;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DefaultResponseService implements ResponseService {
    private final String filename;

    public DefaultResponseService(String filename) {
        this.filename = filename;
    }

    @Override
    public List<Response> listResponses() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            URL filenameUrl = DefaultResponseService.class.getResource("/survey-data/" + filename);
            if (filenameUrl == null) {
                throw new IOException("Resource not found: " + filename);
            }
            return objectMapper.readValue(filenameUrl, new TypeReference<List<Response>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Could not load responses from file: " + filename, e);
        }
    }
}

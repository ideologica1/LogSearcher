package ru.siblion.service.entity.response;


import org.springframework.stereotype.Component;

@Component
public class LogSearchResult {

    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public LogSearchResult() {
    }
}

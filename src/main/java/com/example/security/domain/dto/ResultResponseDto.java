package com.example.security.domain.dto;

public class ResultResponseDto {

    private String result;

    public ResultResponseDto(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

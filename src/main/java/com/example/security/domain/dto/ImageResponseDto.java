package com.example.security.domain.dto;

public class ImageResponseDto {

    private String result;

    private Integer signImageId;

    public ImageResponseDto(String result, Integer signImageId) {
        this.result = result;
        this.signImageId = signImageId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getSignImageId() {
        return signImageId;
    }

    public void setSignImageId(Integer signImageId) {
        this.signImageId = signImageId;
    }
}

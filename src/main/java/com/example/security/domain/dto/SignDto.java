package com.example.security.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class SignDto {

    private Integer id;

    @NotEmpty(message = "Sign needs a title")
    private String title;

    @NotEmpty(message = "Sign needs a description")
    private String description;

    private double lat;

    private double lon;

    private Integer signImageId;

    public SignDto(String title, String description, double lat, double lon) {
        this.title = title;
        this.description = description;
        this.lat = lat;
        this.lon = lon;
    }

    public SignDto(String title, String description, double lat, double lon, Integer imageId) {
        this.title = title;
        this.description = description;
        this.lat = lat;
        this.lon = lon;
        this.signImageId = imageId;
    }

    public SignDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public Integer getSignImageId() {
        return signImageId;
    }

    public void setSignImageId(Integer signImageId) {
        this.signImageId = signImageId;
    }
}

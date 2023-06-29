package com.example.security.domain;

import com.example.security.domain.dto.SignDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class Sign {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    @Column(length = 4096)
    private String description;

    private double lat;

    private double lon;

    @OneToOne(cascade = CascadeType.ALL)
    private SignImage signImage;

    public Sign(Integer id, String title, String description, double lat, double lon) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.lat = lat;
        this.lon = lon;
    }

    public Sign(Integer id, String title, String description, double lat, double lon, SignImage signImage) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.lat = lat;
        this.lon = lon;
        this.signImage = signImage;
    }

    public Sign(String title, String description, double lat, double lon) {
        this.title = title;
        this.description = description;
        this.lat = lat;
        this.lon = lon;
    }

    public Sign() {
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

    public SignImage getSignImage() {
        return signImage;
    }

    public void setSignImage(SignImage signImage) {
        this.signImage = signImage;
    }

    public SignDto dto() {
        SignDto signDto = new SignDto();
        signDto.setId(this.id);
        signDto.setTitle(this.title);
        signDto.setDescription(this.description);
        signDto.setLat(this.lat);
        signDto.setLon(this.lon);
        if (this.signImage != null) {
            signDto.setSignImageId(this.signImage.getId());
        }
        return signDto;
    }
}

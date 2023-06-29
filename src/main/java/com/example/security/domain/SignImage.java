package com.example.security.domain;

import jakarta.persistence.*;

@Entity
public class SignImage {

    @Id
    @GeneratedValue
    private Integer id;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] imageData;

    public SignImage(Integer id, byte[] imageData) {
        this.id = id;
        this.imageData = imageData;
    }

    public SignImage() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}

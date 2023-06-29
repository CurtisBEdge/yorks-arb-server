package com.example.security.service;

import com.example.security.domain.SignImage;
import com.example.security.repository.SignImageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static com.example.security.utils.ImageUtil.compressImage;
import static com.example.security.utils.ImageUtil.decompressImage;

@Service
@Transactional
public class SignImageService {

    private final SignImageRepository signImageRepository;

    public SignImageService(SignImageRepository signImageRepository) {
        this.signImageRepository = signImageRepository;
    }

    public void validateSignImage(MultipartFile signImageFile) {
        InputStream inputStream;
        try {
            inputStream = signImageFile.getInputStream();
            if (ImageIO.read(inputStream) == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not an image.");
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No image found.");
        }
    }

    public byte[] imageCompression(MultipartFile signImageFile) {
        byte[] compressedImage;
        try {
            compressedImage = compressImage(signImageFile.getBytes());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image compression failed.");
        }
        return compressedImage;
    }

    public SignImage saveSignImage(MultipartFile signImageFile) {
        validateSignImage(signImageFile);
        byte[] compressedImage = imageCompression(signImageFile);
        SignImage signImage = new SignImage();
        signImage.setImageData(compressedImage);

        return signImageRepository.save(signImage);
    }

    private SignImage findSignImage(Integer signImageId) {
        return signImageRepository.findById(signImageId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found"));
    }

    public byte[] getSignImage(Integer signImageId) {
        SignImage signImage = findSignImage(signImageId);
        return decompressImage(signImage.getImageData());
    }

    public void deleteSignImage(Integer signImageId) {
        SignImage signImage = findSignImage(signImageId);
        signImageRepository.delete(signImage);
    }

    public byte[] editSignImage(MultipartFile signImageFile ) {
        validateSignImage(signImageFile);
        return imageCompression(signImageFile);

    }
}

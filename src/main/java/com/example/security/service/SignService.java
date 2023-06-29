package com.example.security.service;


import com.example.security.domain.Sign;
import com.example.security.domain.SignImage;
import com.example.security.domain.dto.SignDto;
import com.example.security.repository.SignImageRepository;
import com.example.security.repository.SignRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SignService {

    private final SignRepository signRepository;

    private final SignImageRepository signImageRepository;

    private final SignImageService signImageService;

    public SignService(SignRepository signRepository, SignImageRepository signImageRepository, SignImageService signImageService) {
        this.signRepository = signRepository;
        this.signImageRepository = signImageRepository;
        this.signImageService = signImageService;
    }

    public SignDto addNewSign(String title, String description, double lat, double lon, MultipartFile signImageFile) {
        @Valid SignDto validSign = new SignDto(title, description, lat, lon);
        Sign sign = new Sign();
        if (signImageFile != null && !signImageFile.getOriginalFilename().isEmpty()) {
            SignImage signImage = signImageService.saveSignImage(signImageFile);
            sign.setSignImage(signImage);
        }
        sign.setTitle(title);
        sign.setDescription(description);
        sign.setLat(lat);
        sign.setLon(lon);
        return signRepository.save(sign).dto();
    }

    public void deleteSign(Integer signId) {
        if (!signRepository.existsById(signId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sign not found");
        }
        signRepository.deleteById(signId);
    }

    public List<SignDto> getAllSigns() {
        return signRepository
                .findAll()
                .stream()
                .map(Sign::dto)
                .collect(Collectors.toList());
    }

    public SignDto editSign(Integer signId, String title, String description, double lat, double lon, MultipartFile signImageFile) {
        Sign signToEdit = signRepository.findById(signId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sign not found"));
        @Valid SignDto validSign = new SignDto(title, description, lat, lon);
        if (signImageFile != null && !signImageFile.getOriginalFilename().equals("")) {
            SignImage newSignImage = signImageService.saveSignImage(signImageFile);
            if (signToEdit.getSignImage() != null) {
                signImageRepository.delete(signToEdit.getSignImage());
            }
            signToEdit.setSignImage(newSignImage);
        }
        signToEdit.setTitle(title);
        signToEdit.setDescription(description);
        signToEdit.setLat(lat);
        signToEdit.setLon(lon);
        return signRepository.save(signToEdit).dto();
    }

    public SignDto getSign(Integer signId) {
        Sign sign = signRepository.findById(signId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sign not found"));
        return sign.dto();
    }
}

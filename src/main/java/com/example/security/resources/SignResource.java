package com.example.security.resources;

import com.example.security.domain.dto.ResultResponseDto;
import com.example.security.domain.dto.SignDto;
import com.example.security.service.SignService;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/signs")
public class SignResource {

    private final SignService signService;

    public SignResource(SignService signService) {
        this.signService = signService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultResponseDto> deleteSign(@PathVariable("id") Integer signId) {
        signService.deleteSign(signId);
        return new ResponseEntity<>(new ResultResponseDto("Sign deleted"), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SignDto> addNewSign(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam @Nullable MultipartFile signImage
    ) {
        SignDto createdSign = signService.addNewSign(title, description, lat, lon, signImage);
        return new ResponseEntity<>(createdSign, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<List<SignDto>> getSigns() {
        List<SignDto> signDtos = signService.getAllSigns();
        return new ResponseEntity<>(signDtos, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SignDto> editSign(
            @PathVariable("id") Integer signId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam @Nullable MultipartFile signImage,
            @RequestParam double lat,
            @RequestParam double lon
    ) {
      SignDto editedSignDto = signService.editSign(signId, title, description, lat, lon, signImage);
        return new ResponseEntity<>(editedSignDto, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<SignDto> getSign(@PathVariable("id") Integer signId) {
        SignDto signDto = signService.getSign(signId);
        return new ResponseEntity<>(signDto, HttpStatus.OK);
    }

}

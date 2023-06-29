package com.example.security.resources;

import com.example.security.service.QRCodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/qr-code")
public class QRCodeResource {

    private final QRCodeService qrCodeService;

    public QRCodeResource(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @GetMapping("/{sign-id}")
    public ResponseEntity<byte[]> getQRCode(@PathVariable("sign-id") Integer signId) throws Exception {
        byte[] qrCodeImage = qrCodeService.getQRCode(signId);
        return new ResponseEntity<>(qrCodeImage, HttpStatus.CREATED);
    }
}

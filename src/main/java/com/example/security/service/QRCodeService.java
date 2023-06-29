package com.example.security.service;

import com.example.security.properties.BaseURLProperties;
import com.example.security.repository.SignRepository;
import com.example.security.utils.BaseUrlStringUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class QRCodeService {

    public static final String SIGN_ROUTING = "/signs/";

    public static final String ZXING_ERROR_MESSAGE = "Error creating QR code";

    private final SignRepository signRepository;

    private final ZxingService zxingService;

    private final BaseURLProperties baseURLProperties;

    public QRCodeService(SignRepository signRepository, ZxingService zxingService, BaseURLProperties baseURLProperties) {
        this.signRepository = signRepository;
        this.zxingService = zxingService;
        this.baseURLProperties = baseURLProperties;
    }

    public byte[] getQRCode(Integer signId) {
        if (!signRepository.existsById(signId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sign not found");
        }
        String baseUrl = baseURLProperties.getBaseUrl();
        baseUrl = BaseUrlStringUtil.duplicateSlashRemover(baseUrl);
        String url = baseUrl + SIGN_ROUTING + signId;
        byte[] qrBytes = null;
        try {
            qrBytes = zxingService.getQrBytes(url);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ZXING_ERROR_MESSAGE);
        }
        return qrBytes;
    }
}

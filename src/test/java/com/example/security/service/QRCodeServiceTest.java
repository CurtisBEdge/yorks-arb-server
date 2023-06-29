package com.example.security.service;

import com.example.security.properties.BaseURLProperties;
import com.example.security.repository.SignRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

public class QRCodeServiceTest {

    private final Integer mockSignId = 1;

    @Mock
    private SignRepository signRepositoryMock;

    @Mock
    private BaseURLProperties baseURLPropertiesMock;

    @Mock
    private ZxingService zxingServiceMock;

    @InjectMocks
    private QRCodeService qrCodeServiceTest;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createQRSuccess() throws Exception {
        when(signRepositoryMock.existsById(mockSignId)).thenReturn(true);
        final String url = "https://localhost:3000";
        when(baseURLPropertiesMock.getBaseUrl()).thenReturn(url);
        byte[] mockBytes = new byte[]{24, 12, 56};
        when(zxingServiceMock.getQrBytes(url + mockSignId)).thenReturn(mockBytes);
        byte[] returnedBytes = qrCodeServiceTest.getQRCode(mockSignId);
        assertEquals(mockBytes, returnedBytes);
    }

    @Test
    void createQRFailCannotFindId() {
        when(signRepositoryMock.existsById(mockSignId)).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> qrCodeServiceTest.getQRCode(mockSignId));
    }

    @Test
    void createQRFailNoQRReturned() throws Exception {
        when(signRepositoryMock.existsById(mockSignId)).thenReturn(true);
        final String url = "https://localhost:3000";
        when(baseURLPropertiesMock.getBaseUrl()).thenReturn(url);
        when(zxingServiceMock.getQrBytes(url + mockSignId)).thenThrow(Exception.class);
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            qrCodeServiceTest.getQRCode(mockSignId);
        });
        assertEquals(QRCodeService.ZXING_ERROR_MESSAGE, ex.getReason());
    }


}

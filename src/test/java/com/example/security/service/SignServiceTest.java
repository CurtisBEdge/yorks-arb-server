package com.example.security.service;

import com.example.security.domain.Sign;
import com.example.security.domain.SignImage;
import com.example.security.domain.dto.SignDto;
import com.example.security.repository.SignImageRepository;
import com.example.security.repository.SignRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class SignServiceTest {

    @Mock
    private SignRepository signRepositoryMock;


    @Mock
    private SignImageService signImageServiceMock;


    @InjectMocks
    private SignService signServiceTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void successfulGetSign() {
        Integer signId = 9876;
        Sign fakeSign = new Sign(signId, "title", "Description text", 53.37424053657951, -1.47420922986738);
        when(signRepositoryMock.findById(signId)).thenReturn(Optional.of(fakeSign));
        SignDto signDto = signServiceTest.getSign(signId);
        assertEquals(fakeSign.getId(), signDto.getId());
        assertEquals(fakeSign.getTitle(), signDto.getTitle());
        assertEquals(fakeSign.getDescription(), signDto.getDescription());
        assertEquals(fakeSign.getLat(), signDto.getLat());
        assertEquals(fakeSign.getLon(), signDto.getLon());
    }

    @Test
    void unsuccessfulGetSign() {
        Integer signId = 9876;
        when(signRepositoryMock.findById(signId)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> signServiceTest.getSign(signId));
    }

    @Test
    void addNewSignSuccessWithImage() {
        String title = "my title";
        String description = "my description";
        double lat = 1.23;
        double lon = 2.34;
        MockMultipartFile mockedImage = new MockMultipartFile("treeImage", "treeImage", MediaType.IMAGE_PNG_VALUE, new byte[] {1, 2, 3});
        SignImage signImage = new SignImage(1, new byte[] {1, 2, 3});
        Sign savedSign = new Sign(1, title, description, lat, lon, signImage);
        when(signRepositoryMock.save(any())).thenReturn(savedSign);
        signServiceTest.addNewSign(title, description, lat, lon, mockedImage);
        verify(signImageServiceMock, times(1)).saveSignImage(mockedImage);
    }

    @Test
    void addNewSignSuccessWithoutImage() {
        String title = "my title";
        String description = "my description";
        double lat = 1.23;
        double lon = 2.34;
        MockMultipartFile mockedImage = new MockMultipartFile("tree", "", MediaType.IMAGE_PNG_VALUE, new byte[] {});
        Sign savedSign = new Sign(1, title, description, lat, lon);
        when(signRepositoryMock.save(any())).thenReturn(savedSign);
        signServiceTest.addNewSign(title, description, lat, lon, mockedImage);
        verify(signImageServiceMock, times(0)).saveSignImage(mockedImage);
    }

    @Test
    void addNewSignSuccessWithNullImage() {
        String title = "my title";
        String description = "my description";
        double lat = 1.23;
        double lon = 2.34;
        MultipartFile nullFile = null;
        Sign savedSign = new Sign(1, title, description, lat, lon);
        when(signRepositoryMock.save(any())).thenReturn(savedSign);
        signServiceTest.addNewSign(title, description, lat, lon, nullFile);
        verify(signImageServiceMock, times(0)).saveSignImage(nullFile);
    }

    @Test
    void deleteSignSuccess() {
        Integer signId = 1;
        when(signRepositoryMock.existsById(signId)).thenReturn(true);
        signServiceTest.deleteSign(signId);
        verify(signRepositoryMock, times(1)).deleteById(signId);
    }

    @Test
    void deleteSignFailNoSignId() {
        Integer signId = 1;
        when(signRepositoryMock.existsById(signId)).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> signServiceTest.deleteSign(signId));
    }

    @Test
    void getAllSignsSuccess() {
        Sign sign = new Sign();
        sign.setTitle("my title");
        sign.setDescription("my description");
        sign.setLat(1.23);
        sign.setLon(2.34);
        List<Sign> mockedList = List.of(sign);
        when(signRepositoryMock.findAll()).thenReturn(mockedList);
        List<SignDto> listToCheck =  signServiceTest.getAllSigns();
        assertEquals(listToCheck.size(), 1);
    }

}

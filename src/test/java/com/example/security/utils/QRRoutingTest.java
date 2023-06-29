package com.example.security.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QRRoutingTest {

    @Test
    void successfullyRemoveForwardSlashBaseUrl(){
        String baseUrl = "baseTest/";
        String alteredUrl = BaseUrlStringUtil.duplicateSlashRemover(baseUrl);
        assertEquals( alteredUrl,"baseTest");
    }

    @Test
    void successfullyLeaveBaseUrlUnaltered(){
        String baseUrl = "baseTest";
        String alteredUrl = BaseUrlStringUtil.duplicateSlashRemover(baseUrl);
        assertEquals(alteredUrl,"baseTest");
    }

    @Test
    void successfullyUnalteredDrossPath(){
        String baseUrl = "dross/dross/baseTest";
        String alteredUrl = BaseUrlStringUtil.duplicateSlashRemover(baseUrl);
        assertEquals(alteredUrl,"dross/dross/baseTest");
    }

}

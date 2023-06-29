package com.example.security.utils;

public class BaseUrlStringUtil {

   public static String duplicateSlashRemover(String baseUrl) {
       baseUrl = baseUrl.replaceAll("/$","");
       return baseUrl;
   }
}

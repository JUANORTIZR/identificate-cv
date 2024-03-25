package com.arquitectura.identificatecv.src.utils;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacCalculator {
    public static String calculateSecretHash(String username, String clientId, String clientSecret) {
      try {
        String message = username + clientId;
        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(clientSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256Hmac.init(secretKey);
        byte[] hash = sha256Hmac.doFinal(message.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
      } catch (NoSuchAlgorithmException | InvalidKeyException e) {
        e.printStackTrace();
        return null;
      }
    }
  
    public static String calculateRefreshTokenSecretHash(String refreshToken, String clientId, String clientSecret) {
      try {
        String message = refreshToken + clientId;
  
        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
  
        SecretKeySpec secretKey = new SecretKeySpec(clientSecret.getBytes(), "HmacSHA256");
  
        sha256Hmac.init(secretKey);
  
        byte[] hashBytes = sha256Hmac.doFinal(message.getBytes());
  
        return Base64.getEncoder().encodeToString(hashBytes);
  
      } catch (Exception e) {
        e.printStackTrace();
        return null; 
      }
    }
  }
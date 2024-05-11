package com.arquitectura.identificatecv.config;


import com.amazonaws.services.cognitoidp.model.UserNotConfirmedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getMessage());
    }

    @ExceptionHandler(UserNotConfirmedException.class)
    public ResponseEntity<String> handleUserNotConfirmException(Exception ex) {
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).body("User not confirmed");
    }
}

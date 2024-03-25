package com.arquitectura.identificatecv.src.controller;

import com.arquitectura.identificatecv.src.domain.request.VerificationAccountRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arquitectura.identificatecv.src.domain.request.UserRequest;
import com.arquitectura.identificatecv.src.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("identificate-cv")
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok().body(authService.login(userRequest));
    }

    @PostMapping("/sing-up")
    public ResponseEntity<Boolean> singUp(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok().body(authService.singUp(userRequest));
    }

    @PostMapping("/verification-account")
    public ResponseEntity<Boolean> verificationAccount(@RequestBody VerificationAccountRequest verificationAccountRequest){
        return ResponseEntity.ok().body(authService.verificationAccount(verificationAccountRequest));
    }

    
}

package com.arquitectura.identificatecv.controller;

import com.arquitectura.identificatecv.domain.request.UserRequest;
import com.arquitectura.identificatecv.service.AuthService;
import com.arquitectura.identificatecv.domain.request.VerificationAccountRequest;
import com.arquitectura.identificatecv.domain.response.LoginResponse;
import com.arquitectura.identificatecv.domain.response.SingUpResponse;
import com.arquitectura.identificatecv.domain.response.VerificationAccountResponse;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("identificate-cv")
public class LoginController {

    public static final String USER_ID = "user_id";
    public static final String TOKEN = "token";
    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserRequest userRequest) {
        return authService.login(userRequest);
    }

    @PostMapping("/sing-up")
    public ResponseEntity<SingUpResponse> singUp(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok().body(authService.singUp(userRequest));
    }

    @PostMapping("/verification-account")
    public ResponseEntity<VerificationAccountResponse> verificationAccount(@RequestBody VerificationAccountRequest verificationAccountRequest){
        return ResponseEntity.ok().body(authService.verificationAccount(verificationAccountRequest));
    }

    @PatchMapping("/resend-verification")
    public ResponseEntity verificationAccount(@RequestParam String Nickname){
        return authService.resendCodeVerification(Nickname);
    }



}

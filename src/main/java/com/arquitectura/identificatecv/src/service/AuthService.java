package com.arquitectura.identificatecv.src.service;

import com.arquitectura.identificatecv.src.domain.request.UserRequest;
import com.arquitectura.identificatecv.src.domain.request.VerificationAccountRequest;
import com.arquitectura.identificatecv.src.domain.response.LoginResponse;
import com.arquitectura.identificatecv.src.domain.response.SingUpResponse;
import com.arquitectura.identificatecv.src.domain.response.VerificationAccountResponse;
import org.springframework.stereotype.Service;

import com.arquitectura.identificatecv.src.infrastucture.security.CognitoAuthService;

@Service
public class AuthService {
    
    private final CognitoAuthService cognitoAuthService;

    public AuthService(CognitoAuthService service) {
        this.cognitoAuthService = service;
    }

    public LoginResponse login(UserRequest userRequest){
        try {
            return new LoginResponse(cognitoAuthService.login(userRequest).getAuthenticationResult().getAccessToken());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public SingUpResponse singUp(UserRequest userRequest){
        try {
            return new SingUpResponse(cognitoAuthService.singUp(userRequest).getUserSub());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public VerificationAccountResponse verificationAccount(VerificationAccountRequest verificationAccountRequest){
        try {
            cognitoAuthService.verificationAccount(verificationAccountRequest);
            return new VerificationAccountResponse(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

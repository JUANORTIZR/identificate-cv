package com.arquitectura.identificatecv.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.arquitectura.identificatecv.domain.request.UserRequest;
import com.arquitectura.identificatecv.domain.request.VerificationAccountRequest;
import com.arquitectura.identificatecv.domain.response.LoginResponse;
import com.arquitectura.identificatecv.domain.response.SingUpResponse;
import com.arquitectura.identificatecv.domain.response.VerificationAccountResponse;
import org.springframework.stereotype.Service;

import com.arquitectura.identificatecv.infrastucture.security.CognitoAuthService;

import java.util.List;

@Service
public class AuthService {
    
    private final CognitoAuthService cognitoAuthService;

    public AuthService(CognitoAuthService service) {
        this.cognitoAuthService = service;
    }

    public LoginResponse login(UserRequest userRequest){
        try {
            String tokenAccess = cognitoAuthService.login(userRequest).getAuthenticationResult().getAccessToken();
            List<AttributeType> attributes = cognitoAuthService.getUserDataByToken(tokenAccess).getUserAttributes();
            return new LoginResponse(tokenAccess, attributes);
        } catch (AmazonServiceException e) {
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

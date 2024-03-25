package com.arquitectura.identificatecv.src.service;

import com.arquitectura.identificatecv.src.domain.request.UserRequest;
import org.springframework.stereotype.Service;

import com.arquitectura.identificatecv.src.infrastucture.security.CognitoAuthService;

@Service
public class AuthService {
    
    private final CognitoAuthService cognitoAuthService;

    public AuthService(CognitoAuthService service) {
        this.cognitoAuthService = service;
    }

    public String login(UserRequest userRequest){
        try {
            return cognitoAuthService.login(userRequest).getAuthenticationResult().getAccessToken();
        } catch (Exception e) {
           throw e;
        }
    }

    public Boolean singUp(UserRequest userRequest){
        try {
            return cognitoAuthService.singUp(userRequest).isUserConfirmed();
        } catch (Exception e){
            throw e;
        }
    }


}

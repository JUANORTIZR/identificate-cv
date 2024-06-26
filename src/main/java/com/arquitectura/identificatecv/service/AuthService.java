package com.arquitectura.identificatecv.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.UserNotConfirmedException;
import com.arquitectura.identificatecv.domain.request.UserRequest;
import com.arquitectura.identificatecv.domain.request.VerificationAccountRequest;
import com.arquitectura.identificatecv.domain.response.LoginResponse;
import com.arquitectura.identificatecv.domain.response.SingUpResponse;
import com.arquitectura.identificatecv.domain.response.VerificationAccountResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.arquitectura.identificatecv.infrastucture.security.CognitoAuthService;

import java.util.List;

@Service
public class AuthService {
    
    private final CognitoAuthService cognitoAuthService;

    public AuthService(CognitoAuthService service) {
        this.cognitoAuthService = service;
    }

    public ResponseEntity<LoginResponse> login(UserRequest userRequest){
        try {
            String tokenAccess = cognitoAuthService.login(userRequest).getAuthenticationResult().getAccessToken();
            List<AttributeType> attributes = cognitoAuthService.getUserDataByToken(tokenAccess).getUserAttributes();
            return ResponseEntity.ok().body(new LoginResponse(tokenAccess, attributes));
        }catch (UserNotConfirmedException e){
            cognitoAuthService.resendCode(userRequest.getNickname());
            throw e;
        }
        catch (AmazonServiceException e) {
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

    public ResponseEntity resendCodeVerification(String nickname){
        try {
            cognitoAuthService.resendCode(nickname);
            return ResponseEntity.ok().body(null);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}

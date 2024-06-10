package com.arquitectura.identificatecv.infrastucture.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.arquitectura.identificatecv.config.CognitoConfiguration;
import com.arquitectura.identificatecv.domain.request.UserRequest;
import com.arquitectura.identificatecv.domain.request.VerificationAccountRequest;
import com.arquitectura.identificatecv.utils.HmacCalculator;
import org.springframework.stereotype.Service;
import com.arquitectura.identificatecv.utils.RolNameEnum;

@Service
public class CognitoAuthService {

    public static final String EMAIL = "email";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String NAME = "name";
    public static final String NICKNAME = "nickname";
    public static final String ROL = "custom:Role";
    private final AWSCognitoIdentityProvider identityProvider;

    private final CognitoConfiguration cognitoConfiguration;

    public CognitoAuthService(AWSCognitoIdentityProvider awsCognitoIdentityProvider,
            CognitoConfiguration cognitoConfiguration) {
        this.identityProvider = awsCognitoIdentityProvider;
        this.cognitoConfiguration = cognitoConfiguration;
    }

    public SignUpResult singUp(UserRequest userRequest,String userRol){
        SignUpRequest signUpRequest = new SignUpRequest();
        List<AttributeType> attributeTypes = new ArrayList<>();
        attributeTypes.add(new AttributeType().withName(NICKNAME).withValue(userRequest.getNickname()));
        attributeTypes.add(new AttributeType().withName(NAME).withValue(userRequest.getName()));
        attributeTypes.add(new AttributeType().withName(EMAIL).withValue(userRequest.getEmail()));
        attributeTypes.add(new AttributeType().withName(PHONE_NUMBER).withValue(userRequest.getPhone_number()));
        attributeTypes.add(new AttributeType().withName(ROL).withValue(userRol));
        signUpRequest.withClientId(cognitoConfiguration.getClientId())
                .withUsername(userRequest.getNickname())
                .withPassword(userRequest.getPassword())
                .withUserAttributes(attributeTypes)
                .withSecretHash(Objects.requireNonNull(HmacCalculator.calculateSecretHash(userRequest.getNickname(),
                        cognitoConfiguration.getClientId(), cognitoConfiguration.getClientSecret())));
        return identityProvider.signUp(signUpRequest);
    }

    public SignUpResult singUp(UserRequest userRequest){
        return this.singUp(userRequest,RolNameEnum.ROL_BASE.toString());
    }

    public ResendConfirmationCodeResult resendCode(String nickname){
        ResendConfirmationCodeRequest request = new ResendConfirmationCodeRequest();
        request.withClientId(cognitoConfiguration.getClientId())
               .withUsername(nickname)
               .withSecretHash(Objects.requireNonNull(HmacCalculator.calculateSecretHash(nickname,
                        cognitoConfiguration.getClientId(), cognitoConfiguration.getClientSecret())));
        return identityProvider.resendConfirmationCode(request);
    }

    public ConfirmSignUpResult verificationAccount(VerificationAccountRequest verificationAccountRequest){
        ConfirmSignUpRequest request = new ConfirmSignUpRequest();
        request.withConfirmationCode(verificationAccountRequest.getCode())
                .withClientId(cognitoConfiguration.getClientId())
                .withUsername(verificationAccountRequest.getNickname())
                .withSecretHash(Objects.requireNonNull(HmacCalculator.calculateSecretHash(verificationAccountRequest.getNickname(),
                        cognitoConfiguration.getClientId(), cognitoConfiguration.getClientSecret())));
        return identityProvider.confirmSignUp(request);
    }

    public InitiateAuthResult login(UserRequest userRequest) {
        InitiateAuthRequest initiateAuthRequest = new InitiateAuthRequest();
        initiateAuthRequest.withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH)
                .withClientId(cognitoConfiguration.getClientId())
                .withAuthParameters(Map.of(
                        "USERNAME", userRequest.getNickname(),
                        "PASSWORD", userRequest.getPassword(),
                        "SECRET_HASH", Objects.requireNonNull(HmacCalculator.calculateSecretHash(userRequest.getNickname(),
                                cognitoConfiguration.getClientId(), cognitoConfiguration.getClientSecret()))
                                ));

        return this.identityProvider.initiateAuth(initiateAuthRequest);
    }

    public GetUserResult getUserDataByToken(String token){
        GetUserRequest request = new GetUserRequest();
        request.withAccessToken(token);
        return this.identityProvider.getUser(request);
    }

    public boolean changeRolCompany(UserRequest userRequest){
        AdminUpdateUserAttributesRequest request = new AdminUpdateUserAttributesRequest();
        List<AttributeType> attributeTypes = new ArrayList<>();
        attributeTypes.add(new AttributeType().withName(ROL).withValue(RolNameEnum.ROL_COMPANY.toString()));
        request.withUserPoolId(cognitoConfiguration.getPoolId()).withUsername(userRequest.getNickname()).withUserAttributes(attributeTypes);
        identityProvider.adminUpdateUserAttributes(request);
        return true;
    }



}

package com.arquitectura.identificatecv.src.infrastucture.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.arquitectura.identificatecv.src.config.CognitoConfiguration;
import com.arquitectura.identificatecv.src.domain.request.UserRequest;
import com.arquitectura.identificatecv.src.utils.HmacCalculator;
import org.springframework.stereotype.Service;

@Service
public class CognitoAuthService {

    public static final String EMAIL = "email";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String NAME = "name";
    public static final String NICKNAME = "nickname";
    private final AWSCognitoIdentityProvider identityProvider;

    private final CognitoConfiguration cognitoConfiguration;

    public CognitoAuthService(AWSCognitoIdentityProvider awsCognitoIdentityProvider,
            CognitoConfiguration cognitoConfiguration) {
        this.identityProvider = awsCognitoIdentityProvider;
        this.cognitoConfiguration = cognitoConfiguration;
    }

    public SignUpResult singUp(UserRequest userRequest){
        SignUpRequest signUpRequest = new SignUpRequest();
        List<AttributeType> attributeTypes = new ArrayList<>();
        attributeTypes.add(new AttributeType().withName(NICKNAME).withValue(userRequest.getNickname()));
        attributeTypes.add(new AttributeType().withName(NAME).withValue(userRequest.getName()));
        attributeTypes.add(new AttributeType().withName(EMAIL).withValue(userRequest.getEmail()));
        attributeTypes.add(new AttributeType().withName(PHONE_NUMBER).withValue(userRequest.getPhone_number()));
        signUpRequest.withClientId(cognitoConfiguration.getClientId())
                .withUsername(userRequest.getNickname())
                .withPassword(userRequest.getPassword())
                .withUserAttributes(attributeTypes)
                .withSecretHash(Objects.requireNonNull(HmacCalculator.calculateSecretHash(userRequest.getNickname(),
                        cognitoConfiguration.getClientId(), cognitoConfiguration.getClientSecret())));
        return identityProvider.signUp(signUpRequest);
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

    /*
    public void changePassword(String newPassword, String username){
      // ChangePasswordRequest request = new ChangePasswordRequest().withAccessToken()
              //  ForgotPasswordRequest request = new ForgotPasswordRequest()

        SignUpRequest request = new SignUpRequest().withUserAttributes()
    ConfirmForgotPasswordRequest request = new ConfirmForgotPasswordRequest().
        AdminSetUserPasswordRequest request = new AdminSetUserPasswordRequest().withPassword(newPassword).
                withPermanent(true).withUsername(username);
       identityProvider.adminSetUserPassword(request);
    }
*/


}

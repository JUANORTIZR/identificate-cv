package com.arquitectura.identificatecv.src.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;

@Configuration
public class CognitoConfiguration {

    @Value("${aws.cognito.user-pool-id}")
    private String userPoolId;

    @Value("${aws.cognito.client-id}")
    private String clientId;

    @Value("${aws.cognito.client-secret}")
    private String clientSecret;

    @Value("${aws.cognito.username-field}")
    private String usernameField;

    @Value("${aws.cognito.groups-field}")
    private String groupsField;

    @Value("${cloud.aws.region}")
    private String region;

    @Value("${cloud.aws.credentials.access-key}")
    private String awsAccessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String awsSecretKey;

    @Value("${jwt.header.string}")
    private String httpHeader;

    @Value("${jwt.token.prefix}")
    private String tokenPrefix;

    @Value("${aws.cognito.jwt.use}")
    private String jwtUse;

    @Value("${aws.cognito.issuer.url}")
    private String issuerUrl;

    @Bean
    public AWSCognitoIdentityProvider awsCognitoIdentityProvider() {
        return AWSCognitoIdentityProviderClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
                .withRegion(this.region)
                .build();
    }

    public String getUserPoolId() {
        return userPoolId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getUsernameField() {
        return usernameField;
    }

    public String getGroupsField() {
        return groupsField;
    }

    public String getHttpHeader() {
        return httpHeader;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public String getIssuerUrl() {
        return String.format(issuerUrl, region, userPoolId);
    }

    public String getJwtUse() {
        return jwtUse;
    }
}

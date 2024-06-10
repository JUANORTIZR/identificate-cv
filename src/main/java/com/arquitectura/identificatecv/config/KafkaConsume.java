package com.arquitectura.identificatecv.config;

import com.arquitectura.identificatecv.domain.request.UserRequest;
import com.arquitectura.identificatecv.infrastucture.security.CognitoAuthService;
import com.arquitectura.identificatecv.utils.RolNameEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsume {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsume.class);

    @Autowired
    private CognitoAuthService cognitoAuthService;

    @KafkaListener(topics = "toCreate", groupId = "ConsumerGroup1")
    public void listen(String message) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            UserRequest UserRequest = mapper.readValue(message, UserRequest.class);
            cognitoAuthService.singUp(UserRequest, RolNameEnum.ROL_DOMICILIARY.toString());
        }
        catch (Exception e){
            log.info("error "+e.getMessage());
        }
    }

    @KafkaListener(topics = "toChangeRol", groupId = "ConsumerGroup1")
    public void listenChangeRolCompany(String message) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            UserRequest UserRequest = mapper.readValue(message, UserRequest.class);
            cognitoAuthService.changeRolCompany(UserRequest);
        }
        catch (Exception e){
            log.info("error "+e.getMessage());
        }
    }

}
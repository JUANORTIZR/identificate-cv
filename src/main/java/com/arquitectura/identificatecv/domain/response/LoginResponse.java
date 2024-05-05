package com.arquitectura.identificatecv.domain.response;

import com.amazonaws.services.cognitoidp.model.AttributeType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String tokenAccess;
    List<AttributeType> userAttributes;
}

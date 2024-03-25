package com.arquitectura.identificatecv.src.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String tokenAccess;
}

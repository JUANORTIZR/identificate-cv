package com.arquitectura.identificatecv.src.domain.request;

import lombok.Data;

@Data
public class VerificationAccountRequest {
    private String code;
    private String nickname;
}

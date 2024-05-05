package com.arquitectura.identificatecv.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class VerificationAccountRequest {
    private String code;
    private String nickname;
}

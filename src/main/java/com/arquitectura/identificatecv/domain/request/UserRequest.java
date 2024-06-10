package com.arquitectura.identificatecv.domain.request;

import lombok.Data;

@Data
public class UserRequest {
    private String nickname;
    private String password;
    private String name;
    private String email;
    private String phone_number;
}

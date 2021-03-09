package com.blm.taskme.api.v1.request;

import lombok.Data;

@Data
public class JwtAuthenticationRequest {
    private String email;
    private String password;
}

package com.blm.taskme.api.v1.request;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String nickname;
    private String email;
    private String password;
}

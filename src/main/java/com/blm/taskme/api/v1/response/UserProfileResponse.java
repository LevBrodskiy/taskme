package com.blm.taskme.api.v1.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.Date;

@Data
public class UserProfileResponse {
    private String nickname;
    private String email;
    @JsonAlias("created_at")
    private Date createdAt;
}

package com.blm.taskme.api.v1.response;

import com.blm.taskme.domain.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.Date;

@Data
public class UserResponse {
    private Long id;
    private String nickname;
    private String email;
    private UserStatus status;
    @JsonAlias("created_at")
    private Date createdAt;
    @JsonAlias("updated_at")
    private Date updatedAt;
}

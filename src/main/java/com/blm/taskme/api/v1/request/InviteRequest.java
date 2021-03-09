package com.blm.taskme.api.v1.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class InviteRequest {
    @JsonAlias(value = "email_to")
    private String emailTo;
    @JsonAlias(value = "board_id")
    private Long boardId;
}

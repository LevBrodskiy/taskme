package com.blm.taskme.api.v1.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class InviteResponse {
    @JsonAlias(value = "email_from")
    private String emailFrom;
    @JsonAlias(value = "email_to")
    private String emailTo;
    private LocalDate date;
    @JsonAlias(value = "board_id")
    private Long boardId;
}

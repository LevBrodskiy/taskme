package com.blm.taskme.api.v1.response;

import lombok.Data;

@Data
public class BoardResponse {
    private Long id;
    private String title;
    private String description;
}

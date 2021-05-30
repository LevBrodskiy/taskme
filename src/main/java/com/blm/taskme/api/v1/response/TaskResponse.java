package com.blm.taskme.api.v1.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    @JsonAlias(value = "is_closed")
    private Boolean isClosed;
    @JsonAlias(value = "is_favorite")
    private Boolean isFavorite;
}

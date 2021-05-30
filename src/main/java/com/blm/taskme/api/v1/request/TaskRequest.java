package com.blm.taskme.api.v1.request;

import com.blm.taskme.domain.TaskList;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import javax.persistence.ManyToOne;

@Data
public class TaskRequest {
    private String title;
    private String description;
    @JsonAlias(value = "is_closed")
    private Boolean isClosed;
    @JsonAlias(value = "is_favorite")
    private Boolean isFavorite;
}

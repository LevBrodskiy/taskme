package com.blm.taskme.api.v1.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class BoardRequest {
    @NotBlank
    @Min(value = 3)
    private String title;
    @NotBlank
    private String description;
}

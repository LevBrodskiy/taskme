package com.blm.taskme.api.v1.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorResponse {
    private List<String> messages = new ArrayList<>();

    public void addMessage(String message) {
        messages.add(message);
    }
}

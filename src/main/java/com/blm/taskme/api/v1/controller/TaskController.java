package com.blm.taskme.api.v1.controller;

import com.blm.taskme.api.v1.request.TaskRequest;
import com.blm.taskme.api.v1.response.TaskListResponse;
import com.blm.taskme.api.v1.response.TaskResponse;
import com.blm.taskme.service.exception.EntityNotFoundException;
import com.blm.taskme.service.implementation.DefaultTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/boards/{board_id}/task_lists/{list_id}/tasks")
public class TaskController {
    private final DefaultTaskService taskService;

    @Autowired
    public TaskController(DefaultTaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<?> getTasks(
            @AuthenticationPrincipal Principal principal,
            @PathVariable(name = "board_id") Long boardId,
            @PathVariable(name = "list_id") Long listId
    ) throws EntityNotFoundException {
        List<TaskResponse> response = taskService
                .getTasks(principal, boardId, listId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTask(
            @AuthenticationPrincipal Principal principal,
            @PathVariable(name = "board_id") Long boardId,
            @PathVariable(name = "list_id") Long listId,
            @PathVariable(name = "id") Long id
    ) throws EntityNotFoundException {
        TaskResponse response = taskService
                .getTask(principal, boardId, listId, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addTask(
            @AuthenticationPrincipal Principal principal,
            @PathVariable(name = "board_id") Long boardId,
            @PathVariable(name = "list_id") Long listId,
            @RequestBody TaskRequest request
    ) throws EntityNotFoundException {
        TaskResponse response = taskService
                .addTask(principal, boardId, listId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(
            @AuthenticationPrincipal Principal principal,
            @PathVariable(name = "board_id") Long boardId,
            @PathVariable(name = "list_id") Long listId,
            @PathVariable(name = "id") Long id,
            @RequestBody TaskRequest request
    ) throws EntityNotFoundException {
        taskService.updateTask(principal, boardId, listId, id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(
            @AuthenticationPrincipal Principal principal,
            @PathVariable(name = "board_id") Long boardId,
            @PathVariable(name = "list_id") Long listId,
            @PathVariable(name = "id") Long id
    ) throws EntityNotFoundException {
        taskService.deleteTask(principal, boardId, listId, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

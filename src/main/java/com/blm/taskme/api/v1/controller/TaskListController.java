package com.blm.taskme.api.v1.controller;

import com.blm.taskme.api.v1.request.TaskListRequest;
import com.blm.taskme.api.v1.response.TaskListResponse;
import com.blm.taskme.service.exception.EntityNotFoundException;
import com.blm.taskme.service.implementation.DefaultTaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/boards/{board_id}/task_lists")
public class TaskListController {
    private final DefaultTaskListService taskListService;

    @Autowired
    public TaskListController(DefaultTaskListService taskListService) {
        this.taskListService = taskListService;
    }

    @GetMapping
    public ResponseEntity<?> getLists(
            @AuthenticationPrincipal Principal principal,
            @PathVariable(name = "board_id") Long boardId
    ) throws EntityNotFoundException {
        List<TaskListResponse> response = taskListService
                .getTaskLists(principal, boardId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskList(
            @AuthenticationPrincipal Principal principal,
            @PathVariable(name = "board_id") Long boardId,
            @PathVariable(name = "id") Long id
    ) throws EntityNotFoundException {
        TaskListResponse response = taskListService
                .getTaskList(principal, boardId, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addTaskList(
            @AuthenticationPrincipal Principal principal,
            @PathVariable(name = "board_id") Long boardId,
            @RequestBody TaskListRequest request
    ) throws EntityNotFoundException {
        TaskListResponse response = taskListService
                .addTaskList(principal, boardId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTaskList(
            @AuthenticationPrincipal Principal principal,
            @PathVariable(name = "board_id") Long boardId,
            @PathVariable(name = "id") Long id,
            @RequestBody TaskListRequest request
    ) throws EntityNotFoundException {
        taskListService.updateTaskList(principal, boardId, id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaskList(
            @AuthenticationPrincipal Principal principal,
            @PathVariable(name = "board_id") Long boardId,
            @PathVariable(name = "id") Long id
    ) throws EntityNotFoundException {
        taskListService.deleteTaskList(principal, boardId, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

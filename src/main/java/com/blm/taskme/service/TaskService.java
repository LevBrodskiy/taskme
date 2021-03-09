package com.blm.taskme.service;

import com.blm.taskme.api.v1.request.TaskRequest;
import com.blm.taskme.api.v1.response.TaskResponse;
import com.blm.taskme.service.exception.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

public interface TaskService {
    @Transactional
    TaskResponse addTask(Principal principal, Long boardId,
                         Long taskListId, TaskRequest request)
            throws EntityNotFoundException;

    @Transactional
    TaskResponse updateTask(Principal principal, Long boardId,
                            Long taskListId, Long taskId,
                            TaskRequest request) throws EntityNotFoundException;

    @Transactional
    void deleteTask(Principal principal, Long boardId,
                    Long taskListId, Long taskId) throws EntityNotFoundException;

    @Transactional
    List<TaskResponse> getTasks(Principal principal, Long boardId,
                                Long taskListId) throws EntityNotFoundException;

    @Transactional
    TaskResponse getTask(Principal principal, Long boardId,
                         Long taskListId, Long taskId) throws EntityNotFoundException;
}

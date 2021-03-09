package com.blm.taskme.service;

import com.blm.taskme.api.v1.request.TaskListRequest;
import com.blm.taskme.api.v1.response.TaskListResponse;
import com.blm.taskme.domain.Board;
import com.blm.taskme.domain.TaskList;
import com.blm.taskme.service.annotation.FrontRequest;
import com.blm.taskme.service.exception.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface TaskListService {
    @FrontRequest
    @Transactional
    TaskListResponse addTaskList(Principal principal, Long boardId, TaskListRequest request) throws EntityNotFoundException;

    @Transactional
    void updateTaskList(Principal principal, Long boardId, Long taskListId, TaskListRequest request) throws EntityNotFoundException;

    @Transactional
    void deleteTaskList(Principal principal, Long boardId, Long taskListId) throws EntityNotFoundException;

    @Transactional(readOnly = true)
    List<TaskListResponse> getTaskLists(Principal principal, Long boardId) throws EntityNotFoundException;

    @Transactional(readOnly = true)
    TaskListResponse getTaskList(Principal principal, Long boardId, Long taskListId) throws EntityNotFoundException;

    @Transactional(readOnly = true)
    Optional<TaskList> getByIdAndBoard(Long id, Board board);

    @Transactional
    boolean existsByIdAndBoardId(Long id, Long boardId);
}

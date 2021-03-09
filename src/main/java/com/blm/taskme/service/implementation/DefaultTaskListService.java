package com.blm.taskme.service.implementation;

import com.blm.taskme.api.v1.request.TaskListRequest;
import com.blm.taskme.api.v1.response.TaskListResponse;
import com.blm.taskme.domain.Board;
import com.blm.taskme.domain.TaskList;
import com.blm.taskme.domain.User;
import com.blm.taskme.repository.TaskListRepository;
import com.blm.taskme.service.TaskListService;
import com.blm.taskme.service.annotation.FrontRequest;
import com.blm.taskme.service.exception.EntityNotFoundException;
import com.blm.taskme.service.implementation.DefaultBoardService;
import com.blm.taskme.service.implementation.DefaultUserService;
import com.blm.taskme.service.mapper.TaskListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultTaskListService implements TaskListService {
    private final DefaultUserService userService;
    private final DefaultBoardService boardService;
    private final TaskListMapper taskListMapper;
    private final TaskListRepository taskListRepository;

    @Autowired
    public DefaultTaskListService(DefaultUserService userService, DefaultBoardService boardService, TaskListMapper taskListMapper, TaskListRepository taskListRepository) {
        this.userService = userService;
        this.boardService = boardService;
        this.taskListMapper = taskListMapper;
        this.taskListRepository = taskListRepository;
    }

    @Transactional
    @Override
    public TaskListResponse addTaskList(Principal principal, Long boardId, TaskListRequest request) throws EntityNotFoundException {
        User user = userService.getUser(principal)
                .orElseThrow(() -> new EntityNotFoundException(""));

        Board board = boardService.getBoardByIdAndOwner(boardId, user)
                .orElseThrow(() -> new EntityNotFoundException(""));

        TaskList taskList = new TaskList();
        taskList.setBoard(board);
        taskListMapper.mergeIntoTaskList(taskList, request);

        taskList = taskListRepository.save(taskList);

        return taskListMapper.toResponse(taskList);
    }

    @Transactional
    @Override
    public void updateTaskList(Principal principal, Long boardId, Long taskListId, TaskListRequest request) throws EntityNotFoundException {
        User user = userService.getUser(principal)
                .orElseThrow(() -> new EntityNotFoundException(""));

        Board board = boardService.getBoardByIdAndOwner(boardId, user)
                .orElseThrow(() -> new EntityNotFoundException(""));

        TaskList taskList = taskListRepository.findByIdAndBoard(taskListId, board)
                .orElseThrow(() -> new EntityNotFoundException(""));

        taskListMapper.mergeIntoTaskList(taskList, request);

        taskListRepository.save(taskList);
    }

    @Transactional
    @Override
    public void deleteTaskList(Principal principal, Long boardId, Long taskListId) throws EntityNotFoundException {
        User user = userService.getUser(principal)
                .orElseThrow(() -> new EntityNotFoundException(""));

        Board board = boardService.getBoardByIdAndOwner(boardId, user)
                .orElseThrow(() -> new EntityNotFoundException(""));

        boolean deleted = taskListRepository.deleteByIdAndBoard(taskListId, board);

        if (!deleted) {
            throw new EntityNotFoundException("");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<TaskListResponse> getTaskLists(Principal principal, Long boardId) throws EntityNotFoundException {
        User user = userService.getUser(principal)
                .orElseThrow(() -> new EntityNotFoundException(""));

        Board board = boardService.getBoardByIdAndOwner(boardId, user)
                .orElseThrow(() -> new EntityNotFoundException(""));

        List<TaskList> lists = taskListRepository.findAllByBoard(board);

        return lists
                .stream()
                .map(taskListMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public TaskListResponse getTaskList(Principal principal, Long boardId, Long taskListId) throws EntityNotFoundException {
        User user = userService.getUser(principal)
                .orElseThrow(() -> new EntityNotFoundException(""));

        Board board = boardService.getBoardByIdAndOwner(boardId, user)
                .orElseThrow(() -> new EntityNotFoundException(""));

        TaskList taskList = taskListRepository.findByIdAndBoard(taskListId, board)
                .orElseThrow(() -> new EntityNotFoundException(""));

        return taskListMapper.toResponse(taskList);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<TaskList> getByIdAndBoard(Long id, Board board) {
        return taskListRepository.findByIdAndBoard(id, board);
    }

    @Transactional
    public boolean existsByIdAndBoard(Long id, Board board) {
        return taskListRepository.existsByIdAndBoard(id, board);
    }

    @Transactional
    @Override
    public boolean existsByIdAndBoardId(Long id, Long boardId) {
        return taskListRepository.existsByIdAndBoard_Id(id, boardId);
    }
}

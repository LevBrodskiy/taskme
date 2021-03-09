package com.blm.taskme.service.implementation;

import com.blm.taskme.api.v1.request.TaskRequest;
import com.blm.taskme.api.v1.response.TaskResponse;
import com.blm.taskme.domain.Board;
import com.blm.taskme.domain.Task;
import com.blm.taskme.domain.TaskList;
import com.blm.taskme.domain.User;
import com.blm.taskme.repository.TaskRepository;
import com.blm.taskme.service.TaskService;
import com.blm.taskme.service.exception.EntityNotFoundException;
import com.blm.taskme.service.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultTaskService implements TaskService {
    private final DefaultUserService userService;
    private final DefaultBoardService boardService;
    private final DefaultTaskListService taskListService;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public DefaultTaskService(DefaultUserService userService, DefaultBoardService boardService, DefaultTaskListService taskListService, TaskRepository taskRepository, TaskMapper taskMapper) {
        this.userService = userService;
        this.boardService = boardService;
        this.taskListService = taskListService;
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Transactional
    @Override
    public TaskResponse addTask(Principal principal, Long boardId,
                                Long taskListId, TaskRequest request)
            throws EntityNotFoundException {

        User user = userService.getUser(principal)
                .orElseThrow(() -> new EntityNotFoundException(""));

        Board board = boardService.getBoardByIdAndOwner(boardId, user)
                .orElseThrow(() -> new EntityNotFoundException(""));

        TaskList taskList = taskListService.getByIdAndBoard(taskListId, board)
                .orElseThrow(() -> new EntityNotFoundException(""));

        Task task = new Task();

        taskMapper.mergeIntoTask(task, request);

        task.setId(null);
        task.setTaskList(taskList);

        task = taskRepository.save(task);

        return taskMapper.toResponse(task);
    }

    @Transactional
    @Override
    public TaskResponse updateTask(Principal principal, Long boardId,
                                   Long taskListId, Long taskId,
                                   TaskRequest request) throws EntityNotFoundException {
        User user = userService.getUser(principal)
                .orElseThrow(() -> new EntityNotFoundException(""));

        boolean boardExists = boardService.existsBoardByIdAndOwner(boardId, user);

        if (!boardExists)
            throw new EntityNotFoundException("");

        boolean taskListExists = taskListService.existsByIdAndBoardId(taskListId, boardId);

        if (!taskListExists)
            throw new EntityNotFoundException("");

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException(""));

        taskMapper.mergeIntoTask(task, request);

        task = taskRepository.save(task);

        return taskMapper.toResponse(task);
    }

    @Transactional
    @Override
    public void deleteTask(Principal principal, Long boardId,
                           Long taskListId, Long taskId) throws EntityNotFoundException {
        User user = userService.getUser(principal)
                .orElseThrow(() -> new EntityNotFoundException(""));

        Board board = boardService.getBoardByIdAndOwner(boardId, user)
                .orElseThrow(() -> new EntityNotFoundException(""));

        TaskList taskList = taskListService.getByIdAndBoard(taskListId, board)
                .orElseThrow(() -> new EntityNotFoundException(""));

        boolean exists = taskRepository.existsByIdAndTaskList(taskId, taskList);

        if (!exists) {
            throw new EntityNotFoundException("");
        }

        taskRepository.deleteById(taskId);
    }

    @Transactional
    @Override
    public List<TaskResponse> getTasks(Principal principal, Long boardId,
                                       Long taskListId) throws EntityNotFoundException {
        User user = userService.getUser(principal)
                .orElseThrow(() -> new EntityNotFoundException(""));

        boolean boardExists = boardService.existsBoardByIdAndOwner(boardId, user);

        if (!boardExists) {
            throw new EntityNotFoundException("");
        }

        boolean taskListExists = taskListService
                .existsByIdAndBoardId(taskListId, boardId);

        if (!taskListExists) {
            throw new EntityNotFoundException("");
        }

        List<Task> tasks = taskRepository.findAllByTaskList_Id(taskListId);

        return tasks
                .stream()
                .map(taskMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public TaskResponse getTask(Principal principal, Long boardId,
                                Long taskListId, Long taskId) throws EntityNotFoundException {
        User user = userService.getUser(principal)
                .orElseThrow(() -> new EntityNotFoundException(""));

        boolean boardExists = boardService.existsBoardByIdAndOwner(boardId, user);

        if (!boardExists) {
            throw new EntityNotFoundException("");
        }

        boolean taskListExists = taskListService
                .existsByIdAndBoardId(taskListId, boardId);

        if (!taskListExists) {
            throw new EntityNotFoundException("");
        }

        Task task = taskRepository.findByIdAndTaskList_Id(taskId, taskListId)
                .orElseThrow(() -> new EntityNotFoundException(""));

        return taskMapper.toResponse(task);
    }
}

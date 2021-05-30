package com.blm.taskme.service.implementation;

import com.blm.taskme.api.v1.request.BoardRequest;
import com.blm.taskme.api.v1.response.BoardResponse;
import com.blm.taskme.domain.Board;
import com.blm.taskme.domain.User;
import com.blm.taskme.repository.BoardRepository;
import com.blm.taskme.service.BoardService;
import com.blm.taskme.service.exception.EntityNotFoundException;
import com.blm.taskme.service.exception.MemberAlreadyExistsException;
import com.blm.taskme.service.exception.UserNotFoundException;
import com.blm.taskme.service.mapper.BoardMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultBoardService implements BoardService {
    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;
    private final DefaultUserService userService;

    public DefaultBoardService(BoardRepository boardRepository, BoardMapper boardMapper, DefaultUserService userService) {
        this.boardRepository = boardRepository;
        this.boardMapper = boardMapper;
        this.userService = userService;
    }

    @Transactional
    @Override
    public BoardResponse addBoard(Principal principal, BoardRequest request) throws UserNotFoundException {
        User user = userService.getUser(principal)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Board board = new Board();

        boardMapper.mergeIntoBoard(board, request);

        board.setId(null);
        board.setOwner(user);
        board.setIsFavorite(false);
        Board savedBoard = boardRepository.save(board);

        return boardMapper.toResponse(savedBoard);
    }

    @Transactional
    @Override
    public void updateBoard(Principal principal, Long boardId, BoardRequest request) throws EntityNotFoundException {
        User user = userService.getUser(principal)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Board board = boardRepository.findByIdAndOwner(boardId, user)
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));

        boardMapper.mergeIntoBoard(board, request);

        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BoardResponse> getBoards(Principal principal, int page, int perPage) throws UserNotFoundException {
        User user = userService.getUser(principal)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Pageable pageable = PageRequest.of(page, perPage);

        List<Board> boards = boardRepository.findByOwner_Id(user.getId(), pageable).toList();

        List<BoardResponse> responseList = boards
                .stream()
                .map(boardMapper::toResponse)
                .collect(Collectors.toList());

        return responseList;
    }

    @Transactional
    @Override
    public void deleteBoard(Principal principal, long boardId) throws EntityNotFoundException {
        User user = userService.getUser(principal)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Integer deleted = boardRepository.deleteByIdAndOwner(boardId, user);

        if (deleted == 0) {
            throw new EntityNotFoundException("Board not found");
        }
    }

    @Transactional
    @Override
    public BoardResponse getBoard(Principal principal, Long boardId) throws EntityNotFoundException {
        User user = userService.getUser(principal)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Board board = boardRepository.findByIdAndOwner(boardId, user)
                .orElseThrow(() -> new EntityNotFoundException(""));

        return boardMapper.toResponse(board);
    }

    @Transactional
    @Override
    public Optional<Board> getBoardByIdAndOwner(Long id, User owner) {
        return boardRepository.findByIdAndOwner(id, owner);
    }

    @Transactional
    @Override
    public boolean existsBoardByIdAndOwner(Long id, User owner) {
        return boardRepository.existsByIdAndOwner(id, owner);
    }

    @Transactional
    @Override
    public void addMember(Long boardId, User member) throws EntityNotFoundException, MemberAlreadyExistsException {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException(""));

        if (board.hasMember(member.getId())) {
            throw new MemberAlreadyExistsException("");
        }

        board.addMember(member);

        boardRepository.save(board);
    }
}

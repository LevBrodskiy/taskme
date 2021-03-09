package com.blm.taskme.service;

import com.blm.taskme.api.v1.request.BoardRequest;
import com.blm.taskme.api.v1.response.BoardResponse;
import com.blm.taskme.domain.Board;
import com.blm.taskme.domain.User;
import com.blm.taskme.service.exception.EntityNotFoundException;
import com.blm.taskme.service.exception.MemberAlreadyExistsException;
import com.blm.taskme.service.exception.UserNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface BoardService {
    BoardResponse addBoard(Principal principal, BoardRequest request)
            throws UserNotFoundException;

    void updateBoard(Principal principal, Long boardId, BoardRequest request)
            throws EntityNotFoundException;

    List<BoardResponse> getBoards(Principal principal, int page, int perPage)
            throws UserNotFoundException;

    void deleteBoard(Principal principal, long boardId)
            throws EntityNotFoundException;

    BoardResponse getBoard(Principal principal, Long boardId)
            throws EntityNotFoundException;

    Optional<Board> getBoardByIdAndOwner(Long id, User owner);

    boolean existsBoardByIdAndOwner(Long id, User owner);

    void addMember(Long boardId, User member)
            throws EntityNotFoundException, MemberAlreadyExistsException;
}

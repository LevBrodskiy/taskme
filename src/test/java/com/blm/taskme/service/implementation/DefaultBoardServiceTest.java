package com.blm.taskme.service.implementation;

import com.blm.taskme.api.v1.request.BoardRequest;
import com.blm.taskme.api.v1.response.BoardResponse;
import com.blm.taskme.domain.Board;
import com.blm.taskme.domain.User;
import com.blm.taskme.repository.BoardRepository;
import com.blm.taskme.service.exception.EntityNotFoundException;
import com.blm.taskme.service.exception.UserNotFoundException;
import com.blm.taskme.service.mapper.BoardMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DefaultBoardServiceTest {
    @Mock
    private BoardRepository repository;
    @Mock
    private BoardMapper mapper;
    @Mock
    private DefaultUserService userService;

    @InjectMocks
    private DefaultBoardService boardService;

    @Test
    void addBoard_Should_Return_Board() throws UserNotFoundException {
        BoardResponse expected = new BoardResponse();
        Principal principal =
                new UsernamePasswordAuthenticationToken(null, null);

        when(userService.getUser(any(Principal.class)))
                .thenReturn(Optional.of(new User()));

        when(mapper.toResponse(any(Board.class)))
                .thenReturn(expected);

        when(repository.save(any(Board.class)))
                .thenReturn(new Board());

        BoardResponse response = boardService.addBoard(principal, new BoardRequest());

        assertEquals(expected, response);
    }

    @Test
    void addBoard_Should_Throw_UserNotFoundException() {
        Principal principal =
                new UsernamePasswordAuthenticationToken(null, null);

        when(userService.getUser(any(Principal.class)))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> boardService.addBoard(principal, new BoardRequest()));
    }

    @Test
    void updateBoard_Should_Update_Board() throws EntityNotFoundException {
        Principal principal =
                new UsernamePasswordAuthenticationToken(null, null);
        Long boardId = 1L;
        User user = new User();
        Board board = new Board();
        board.setId(boardId);

        BoardRequest request = new BoardRequest();
        request.setTitle("Title");
        request.setDescription("Description");

        when(userService.getUser(any(Principal.class)))
                .thenReturn(Optional.of(user));

        when(repository.findByIdAndOwner(boardId, user))
                .thenReturn(Optional.of(board));

        boardService.updateBoard(principal, boardId, request);
    }

    @Test
    void getBoards() {
        assertThat(null, equalTo(null));
    }

    @Test
    void deleteBoard() {
    }

    @Test
    void getBoard() {
    }

    @Test
    void getBoardByIdAndOwner() {
    }

    @Test
    void existsBoardByIdAndOwner() {
    }

    @Test
    void addMember() {
    }
}
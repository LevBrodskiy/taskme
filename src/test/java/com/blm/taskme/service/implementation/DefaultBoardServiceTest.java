package com.blm.taskme.service.implementation;

import com.blm.taskme.api.v1.request.BoardRequest;
import com.blm.taskme.api.v1.response.BoardResponse;
import com.blm.taskme.domain.Board;
import com.blm.taskme.domain.User;
import com.blm.taskme.repository.BoardRepository;
import com.blm.taskme.service.exception.EntityNotFoundException;
import com.blm.taskme.service.exception.MemberAlreadyExistsException;
import com.blm.taskme.service.exception.UserNotFoundException;
import com.blm.taskme.service.mapper.BoardMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
    void getBoards_Should_Return_boards() throws UserNotFoundException {
        User user = new User();
        user.setId(3L);

        when(userService.getUser(any(Principal.class)))
                .thenReturn(Optional.of(user));

        List<Board> returnedList = new ArrayList<>();
        returnedList.add(new Board());
        returnedList.add(new Board());

        Page<Board> page = new PageImpl<Board>(returnedList);

        when(repository.findByOwner_Id(eq(3L), any(Pageable.class)))
                .thenReturn(page);

        List<BoardResponse> response = boardService.getBoards(new UsernamePasswordAuthenticationToken("", ""), 0, 2);

        assertThat(response.size(), is(equalTo(2)));
    }

    @Test
    void deleteBoard() throws EntityNotFoundException {
        Long boardId = 3L;
        User user = new User();
        Integer deletedBoards = 1;

        when(userService.getUser(any(Principal.class)))
                .thenReturn(Optional.of(user));

        when(repository.deleteByIdAndOwner(boardId, user))
                .thenReturn(deletedBoards);

        boardService.deleteBoard(new UsernamePasswordAuthenticationToken(null, null), boardId);
    }

    @Test
    void deleteBoard_Should_Throw_Exception() {
        Long boardId = 3L;
        User user = new User();
        Integer deletedBoards = 0;

        when(userService.getUser(any(Principal.class)))
                .thenReturn(Optional.of(user));

        when(repository.deleteByIdAndOwner(boardId, user))
                .thenReturn(deletedBoards);


        assertThrows(EntityNotFoundException.class, () -> {
            boardService.deleteBoard(new UsernamePasswordAuthenticationToken(null, null), boardId);
        });
    }

    @Test
    void getBoard_Should_Return_BoardResponse() throws EntityNotFoundException {
        Principal principal = new UsernamePasswordAuthenticationToken("", "");
        Long boardId = 5L;
        User user = new User();
        Board returnedBoard = new Board();
        BoardResponse boardResponse = new BoardResponse();

        when(userService.getUser(any(Principal.class)))
                .thenReturn(Optional.of(user));

        when(repository.findByIdAndOwner(boardId, user))
                .thenReturn(Optional.of(returnedBoard));

        when(mapper.toResponse(returnedBoard))
                .thenReturn(boardResponse);

        BoardResponse response = boardService.getBoard(principal, boardId);

        assertThat(response, equalTo(boardResponse));
    }

    @Test
    void addMember() throws MemberAlreadyExistsException, EntityNotFoundException {
        Long boardId = 3L;
        User member = new User();
        Board returnedBoard = Mockito.spy(new Board());

        when(repository.findById(eq(boardId)))
                .thenReturn(Optional.of(returnedBoard));

        boardService.addMember(boardId, member);

        verify(returnedBoard, times(1)).addMember(member);
    }
}
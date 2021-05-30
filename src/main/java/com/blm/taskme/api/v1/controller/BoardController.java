package com.blm.taskme.api.v1.controller;

import com.blm.taskme.api.v1.request.BoardRequest;
import com.blm.taskme.api.v1.response.BoardResponse;
import com.blm.taskme.service.exception.EntityNotFoundException;
import com.blm.taskme.service.exception.UserNotFoundException;
import com.blm.taskme.service.implementation.DefaultBoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/boards")
public class BoardController {
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    private final DefaultBoardService boardService;

    @Autowired
    public BoardController(DefaultBoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public ResponseEntity<?> getBoards(
            @AuthenticationPrincipal Principal principal,
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "per_page", defaultValue = "50", required = false) Integer perPage
    ) throws EntityNotFoundException {
        logger.debug("Get boards request: page={} per_page={}", page, perPage);

        List<BoardResponse> response = boardService.getBoards(principal, page, perPage);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBoard(
            @AuthenticationPrincipal Principal principal,
            @PathVariable(name = "id") Long id
    ) throws EntityNotFoundException {
        BoardResponse response = boardService.getBoard(principal, id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addBoard(
            @AuthenticationPrincipal Principal principal,
            @RequestBody BoardRequest request
    ) throws UserNotFoundException {
        BoardResponse response = boardService.addBoard(principal, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBoard(
            @AuthenticationPrincipal Principal principal,
            @PathVariable(name = "id") Long id,
            @RequestBody BoardRequest request
    ) throws EntityNotFoundException {
        boardService.updateBoard(principal, id, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(
            @AuthenticationPrincipal Principal principal,
            @PathVariable(name = "id") Long id
    ) throws EntityNotFoundException {
        boardService.deleteBoard(principal, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

package com.blm.taskme.api.v1.controller;

import com.blm.taskme.api.v1.request.InviteRequest;
import com.blm.taskme.api.v1.response.InviteResponse;
import com.blm.taskme.service.exception.EntityNotFoundException;
import com.blm.taskme.service.exception.InvalidOperationException;
import com.blm.taskme.service.exception.MemberAlreadyExistsException;
import com.blm.taskme.service.implementation.DefaultInviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/ap1/v1/invites")
public class InviteController {
    private final DefaultInviteService inviteService;

    @Autowired
    public InviteController(DefaultInviteService inviteService) {
        this.inviteService = inviteService;
    }

    @GetMapping("/incoming")
    public ResponseEntity<?> getIncomingInvites(
            @AuthenticationPrincipal Principal principal
    ) throws EntityNotFoundException {
        List<InviteResponse> response = inviteService
                .getIncomingInvites(principal);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/outgoing")
    public ResponseEntity<?> getOutgoing(
            @AuthenticationPrincipal Principal principal
    ) throws EntityNotFoundException {
        List<InviteResponse> response = inviteService
                .getOutgoingInvites(principal);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addInvite(
            @AuthenticationPrincipal Principal principal,
            @RequestBody InviteRequest request
    ) throws EntityNotFoundException {
        inviteService.addInvite(principal, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectInvite(
            @AuthenticationPrincipal Principal principal,
            @PathVariable(name = "id") Long id
    ) throws InvalidOperationException, EntityNotFoundException {
        inviteService.rejectInvite(principal, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<?> acceptInvite(
            @AuthenticationPrincipal Principal principal,
            @PathVariable(name = "id") Long id
    ) throws EntityNotFoundException, MemberAlreadyExistsException {
        inviteService.acceptInvite(principal, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

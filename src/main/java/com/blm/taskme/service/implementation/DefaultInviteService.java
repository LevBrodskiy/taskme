package com.blm.taskme.service.implementation;

import com.blm.taskme.api.v1.request.InviteRequest;
import com.blm.taskme.api.v1.response.InviteResponse;
import com.blm.taskme.domain.Board;
import com.blm.taskme.domain.Invite;
import com.blm.taskme.domain.User;
import com.blm.taskme.domain.enums.InviteStatus;
import com.blm.taskme.repository.InviteRepository;
import com.blm.taskme.service.exception.EntityNotFoundException;
import com.blm.taskme.service.exception.InvalidOperationException;
import com.blm.taskme.service.exception.MemberAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultInviteService {
    private final InviteRepository inviteRepository;
    private final DefaultUserService userService;
    private final DefaultBoardService boardService;

    @Autowired
    public DefaultInviteService(InviteRepository inviteRepository, DefaultUserService userService, DefaultBoardService boardService) {
        this.inviteRepository = inviteRepository;
        this.userService = userService;
        this.boardService = boardService;
    }

    @Transactional
    public void addInvite(Principal principal, InviteRequest request) throws EntityNotFoundException {
        User sender = userService.getUser(principal)
                .orElseThrow(() -> new EntityNotFoundException(""));

        User user = userService.getUserByEmail(request.getEmailTo())
                .orElseThrow(() -> new EntityNotFoundException(""));

        Board board = boardService.getBoardByIdAndOwner(request.getBoardId(), sender)
                .orElseThrow(() -> new EntityNotFoundException(""));

        Invite invite = Invite
                .builder()
                .id(null)
                .fromUser(sender)
                .toUser(user)
                .status(InviteStatus.WAITING)
                .board(board)
                .build();

        System.out.println(invite);

        invite = inviteRepository.save(invite);
    }

    @Transactional
    public void deleteInvite(Principal principal, Long id) throws EntityNotFoundException, InvalidOperationException {
        User user = userService.getUser(principal)
                .orElseThrow(() -> new EntityNotFoundException(""));

        Invite invite = inviteRepository.findByIdAndFromUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException(""));

        if (invite.getStatus() != InviteStatus.WAITING) {
            throw new InvalidOperationException("");
        }

        invite.setStatus(InviteStatus.DELETED);

        inviteRepository.save(invite);
    }

    @Transactional
    public void acceptInvite(Principal principal, Long id) throws EntityNotFoundException, MemberAlreadyExistsException {
        User user = userService.getUser(principal)
                .orElseThrow(() -> new EntityNotFoundException(""));

        Invite invite = inviteRepository.findByIdAndToUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException(""));

        Board board = invite.getBoard();

        boardService.addMember(board.getId(), user);

        invite.setStatus(InviteStatus.ACCEPTED);
    }

    @Transactional
    public void rejectInvite(Principal principal, Long id) throws EntityNotFoundException, InvalidOperationException {
        User user = userService.getUser(principal)
                .orElseThrow(() -> new EntityNotFoundException(""));

        Invite invite = inviteRepository.findByIdAndFromUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException(""));

        if (invite.getStatus() != InviteStatus.WAITING) {
            throw new InvalidOperationException("");
        }

        invite.setStatus(InviteStatus.REJECTED);

        inviteRepository.save(invite);
    }

    @Transactional
    public List<InviteResponse> getOutgoingInvites(Principal principal) throws EntityNotFoundException {
        User user = userService.getUser(principal)
                .orElseThrow(() -> new EntityNotFoundException(""));

        List<Invite> invites = inviteRepository.findAllByFromUser(user);

        return invites
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<InviteResponse> getIncomingInvites(Principal principal) throws EntityNotFoundException {
        User user = userService.getUser(principal)
                .orElseThrow(() -> new EntityNotFoundException(""));

        List<Invite> invites = inviteRepository.findAllByToUser(user);

        return invites
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private InviteResponse toResponse(Invite invite) {
        InviteResponse response = InviteResponse
                .builder()
                .id(invite.getId())
                .emailFrom(invite.getFromUser().getEmail())
                .emailTo(invite.getToUser().getEmail())
                .boardId(invite.getBoard().getId())
                .date(invite.getCreatedAt())
                .build();
        return response;
    }
}

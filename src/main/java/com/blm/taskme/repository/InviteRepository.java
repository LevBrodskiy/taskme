package com.blm.taskme.repository;

import com.blm.taskme.domain.Invite;
import com.blm.taskme.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InviteRepository extends JpaRepository<Invite, Long> {
    List<Invite> findAllByFromUser(User fromUser);
    List<Invite> findAllByToUser(User toUser);
    Optional<Invite> findByIdAndFromUser(Long id, User fromUser);
    Optional<Invite> findByIdAndToUser(Long id, User toUser);
}

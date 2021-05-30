package com.blm.taskme.domain;

import com.blm.taskme.domain.enums.InviteStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Entity
@Table(name = "invites")
public class Invite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private User fromUser;
    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private User toUser;
    @ManyToOne
    private Board board;
    @Enumerated(value = EnumType.STRING)
    private InviteStatus status;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @PrePersist
    private void prePersist() {
        createdAt = LocalDate.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = LocalDate.now();
    }

}

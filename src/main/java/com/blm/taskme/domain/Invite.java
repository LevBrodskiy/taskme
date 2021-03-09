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
    private User fromUser;
    @ManyToOne
    private User toUser;
    @ManyToOne
    private Board board;
    @Enumerated(value = EnumType.STRING)
    private InviteStatus status;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @Column(name = "updated_at")
    private LocalDate updatedAt;
}

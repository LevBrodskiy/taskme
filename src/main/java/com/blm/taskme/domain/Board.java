package com.blm.taskme.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ToString(of = {"id", "title"})
@Data
@Entity
@Table(name = "boards")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Boolean isFavorite;
    @ManyToMany
    @JoinTable(name = "boards_members",
            joinColumns = @JoinColumn(name = "board_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
    private List<User> members = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
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


    public boolean hasMember(Long memberId) {
        return members
                .stream()
                .anyMatch(m -> m.getId().equals(memberId));
    }

    public void addMember(User user) {
        members.add(user);
    }
}

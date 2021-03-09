package com.blm.taskme.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    private List<User> members;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    @Temporal(value = TemporalType.DATE)
    @Column(name = "created_at")
    private Date createdAt;
    @Temporal(value = TemporalType.DATE)
    @Column(name = "updated_at")
    private Date updatedAt;

    public boolean hasMember(Long memberId) {
        return members
                .stream()
                .anyMatch(m -> m.getId().equals(memberId));
    }

    public void addMember(User user) {
        members.add(user);
    }
}

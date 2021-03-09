package com.blm.taskme.domain;

import com.blm.taskme.domain.enums.UserStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private UserStatus status;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;
    @Temporal(value = TemporalType.DATE)
    @Column(name = "created_at")
    private Date createdAt;
    @Temporal(value = TemporalType.DATE)
    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    private void prePersist() {
        createdAt = new Date();
        updatedAt = createdAt;
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = new Date();
    }

    public void addRole(Role role) {
        roles.add(role);
    }
}

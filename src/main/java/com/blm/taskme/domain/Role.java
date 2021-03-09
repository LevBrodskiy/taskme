package com.blm.taskme.domain;

import com.blm.taskme.domain.enums.RoleName;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private RoleName name;
    @OneToMany
    @JoinTable(name = "roles_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private List<Permission> permissions;
    @Temporal(value = TemporalType.DATE)
    @Column(name = "created_at")
    private Date createdAt;
    @Temporal(value = TemporalType.DATE)
    @Column(name = "updated_at")
    private Date updatedAt;
}

package com.blm.taskme.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Boolean isClosed;
    private Boolean isFavorite;
    @ManyToOne
    private TaskList taskList;
    @Temporal(value = TemporalType.DATE)
    @Column(name = "created_at")
    private Date createdAt;
    @Temporal(value = TemporalType.DATE)
    @Column(name = "updated_at")
    private Date updatedAt;
}

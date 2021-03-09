package com.blm.taskme.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "task_lists")
public class TaskList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne
    private Board board;
}

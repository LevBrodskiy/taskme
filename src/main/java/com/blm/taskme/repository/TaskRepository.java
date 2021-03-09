package com.blm.taskme.repository;

import com.blm.taskme.domain.Task;
import com.blm.taskme.domain.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    boolean existsByIdAndTaskList(Long id, TaskList taskList);
    List<Task> findAllByTaskList_Id(Long taskListId);
    Optional<Task> findByIdAndTaskList_Id(Long id, Long taskListId);
}

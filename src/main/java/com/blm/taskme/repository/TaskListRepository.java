package com.blm.taskme.repository;

import com.blm.taskme.domain.Board;
import com.blm.taskme.domain.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    Optional<TaskList> findByIdAndBoard(Long id, Board board);
    boolean existsByIdAndBoard_Id(Long id, Long boardId);
    Integer deleteByIdAndBoard(Long id, Board board);
    List<TaskList> findAllByBoard(Board board);
    boolean existsByIdAndBoard(Long id, Board board);
}

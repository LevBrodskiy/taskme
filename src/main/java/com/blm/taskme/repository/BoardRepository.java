package com.blm.taskme.repository;

import com.blm.taskme.domain.Board;
import com.blm.taskme.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends PagingAndSortingRepository<Board, Long> {
    List<Board> findByOwner(User owner);
    Page<Board> findByOwner_Id(Long ownerId, Pageable page);
    Optional<Board> findByIdAndOwner(Long id, User owner);
    boolean existsByIdAndOwner(Long id, User owner);
    Integer deleteByIdAndOwner(Long id, User owner);
}

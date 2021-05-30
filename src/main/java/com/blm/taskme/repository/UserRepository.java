package com.blm.taskme.repository;

import com.blm.taskme.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    @EntityGraph(attributePaths = "roles")
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<Long> findIdByEmail(String email);
}

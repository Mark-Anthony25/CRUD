package com.mycoolestapp.repository;

import com.mycoolestapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    <optional>User findByEmail(String email);
    boolean existsByEmail(String email);
}


package com.nisum.directory.john.repository;

import com.nisum.directory.john.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {
    boolean existsByEmail(String email);
}


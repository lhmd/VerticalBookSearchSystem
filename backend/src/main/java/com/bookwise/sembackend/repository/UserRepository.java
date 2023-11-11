package com.bookwise.sembackend.repository;

import com.bookwise.sembackend.db_model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findUserByUsername(String username);
}

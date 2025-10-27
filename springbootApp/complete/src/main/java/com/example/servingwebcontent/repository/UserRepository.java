package com.example.servingwebcontent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.servingwebcontent.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
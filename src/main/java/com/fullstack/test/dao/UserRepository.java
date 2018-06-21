package com.fullstack.test.dao;

import com.fullstack.test.domain.User;
import com.fullstack.test.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findByRole(UserRole role);
}

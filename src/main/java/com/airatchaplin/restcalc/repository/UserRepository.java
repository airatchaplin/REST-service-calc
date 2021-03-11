package com.airatchaplin.restcalc.repository;

import com.airatchaplin.restcalc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
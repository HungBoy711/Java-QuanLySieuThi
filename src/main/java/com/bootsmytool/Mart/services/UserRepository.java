package com.bootsmytool.Mart.services;

import com.bootsmytool.Mart.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username); 
}

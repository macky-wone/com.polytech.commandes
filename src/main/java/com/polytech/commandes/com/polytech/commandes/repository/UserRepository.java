package com.polytech.commandes.com.polytech.commandes.repository;

import com.polytech.commandes.com.polytech.commandes.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
}

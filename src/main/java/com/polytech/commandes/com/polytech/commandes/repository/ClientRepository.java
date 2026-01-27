package com.polytech.commandes.com.polytech.commandes.repository;

import com.polytech.commandes.com.polytech.commandes.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);

    boolean existsByEmail(String email);
}

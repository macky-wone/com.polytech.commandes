package com.polytech.commandes.com.polytech.commandes.repository;

import com.polytech.commandes.com.polytech.commandes.entity.Commande;
import com.polytech.commandes.com.polytech.commandes.entity.StatusCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    List<Commande> findByClientId(Long clientId);

    List<Commande> findByStatus(StatusCommande status);
}

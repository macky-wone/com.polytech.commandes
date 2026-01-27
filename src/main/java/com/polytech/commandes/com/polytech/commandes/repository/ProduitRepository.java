package com.polytech.commandes.com.polytech.commandes.repository;

import com.polytech.commandes.com.polytech.commandes.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    Optional<Produit> findByNom(String nom);

    boolean existsByNom(String nom);
}

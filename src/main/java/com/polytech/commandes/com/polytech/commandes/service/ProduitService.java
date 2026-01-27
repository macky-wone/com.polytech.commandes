package com.polytech.commandes.com.polytech.commandes.service;

import com.polytech.commandes.com.polytech.commandes.dto.ProduitDTO;
import java.util.List;
import java.util.Optional;

public interface ProduitService {
    ProduitDTO createProduit(ProduitDTO produitDTO);

    Optional<ProduitDTO> getProduitById(Long id);

    ProduitDTO updateProduit(Long id, ProduitDTO produitDTO);

    void deleteProduit(Long id);

    List<ProduitDTO> getAllProduits();

    Optional<ProduitDTO> getProduitByNom(String nom);

    void updateStock(Long id, Integer quantite);
}

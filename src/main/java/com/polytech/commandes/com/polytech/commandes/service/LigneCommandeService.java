package com.polytech.commandes.com.polytech.commandes.service;

import com.polytech.commandes.com.polytech.commandes.dto.LigneCommandeDTO;
import java.util.List;
import java.util.Optional;

public interface LigneCommandeService {
    LigneCommandeDTO createLigneCommande(LigneCommandeDTO ligneCommandeDTO);

    Optional<LigneCommandeDTO> getLigneCommandeById(Long id);

    LigneCommandeDTO updateLigneCommande(Long id, LigneCommandeDTO ligneCommandeDTO);

    void deleteLigneCommande(Long id);

    List<LigneCommandeDTO> getAllLignesCommande();

    List<LigneCommandeDTO> getLignesCommandeByCommandeId(Long commandeId);
}

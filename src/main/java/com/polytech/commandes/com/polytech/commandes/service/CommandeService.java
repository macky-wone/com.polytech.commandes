package com.polytech.commandes.com.polytech.commandes.service;

import com.polytech.commandes.com.polytech.commandes.dto.CommandeDTO;
import com.polytech.commandes.com.polytech.commandes.entity.StatusCommande;
import java.util.List;
import java.util.Optional;

public interface CommandeService {
    CommandeDTO createCommande(CommandeDTO commandeDTO);

    Optional<CommandeDTO> getCommandeById(Long id);

    CommandeDTO updateCommande(Long id, CommandeDTO commandeDTO);

    void deleteCommande(Long id);

    List<CommandeDTO> getAllCommandes();

    List<CommandeDTO> getCommandesByClientId(Long clientId);

    List<CommandeDTO> getCommandesByStatus(StatusCommande status);

    CommandeDTO updateCommandeStatus(Long id, StatusCommande status);
}

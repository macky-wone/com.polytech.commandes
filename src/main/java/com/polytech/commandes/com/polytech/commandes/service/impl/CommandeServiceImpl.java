package com.polytech.commandes.com.polytech.commandes.service.impl;

import com.polytech.commandes.com.polytech.commandes.dto.CommandeDTO;
import com.polytech.commandes.com.polytech.commandes.entity.Client;
import com.polytech.commandes.com.polytech.commandes.entity.Commande;
import com.polytech.commandes.com.polytech.commandes.entity.StatusCommande;
import com.polytech.commandes.com.polytech.commandes.repository.ClientRepository;
import com.polytech.commandes.com.polytech.commandes.repository.CommandeRepository;
import com.polytech.commandes.com.polytech.commandes.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommandeServiceImpl implements CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public CommandeDTO createCommande(CommandeDTO commandeDTO) {
        Client client = clientRepository.findById(commandeDTO.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        Commande commande = new Commande(client);
        Commande savedCommande = commandeRepository.save(commande);
        return mapToDTO(savedCommande);
    }

    @Override
    public Optional<CommandeDTO> getCommandeById(Long id) {
        return commandeRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    public CommandeDTO updateCommande(Long id, CommandeDTO commandeDTO) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Commande not found"));

        if (commandeDTO.getStatus() != null) {
            commande.setStatus(commandeDTO.getStatus());
        }

        Commande updatedCommande = commandeRepository.save(commande);
        return mapToDTO(updatedCommande);
    }

    @Override
    public void deleteCommande(Long id) {
        commandeRepository.deleteById(id);
    }

    @Override
    public List<CommandeDTO> getAllCommandes() {
        return commandeRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommandeDTO> getCommandesByClientId(Long clientId) {
        return commandeRepository.findByClientId(clientId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommandeDTO> getCommandesByStatus(StatusCommande status) {
        return commandeRepository.findByStatus(status).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CommandeDTO updateCommandeStatus(Long id, StatusCommande status) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Commande not found"));

        commande.setStatus(status);
        Commande updatedCommande = commandeRepository.save(commande);
        return mapToDTO(updatedCommande);
    }

    private CommandeDTO mapToDTO(Commande commande) {
        return new CommandeDTO(
                commande.getId(),
                commande.getDateCommande(),
                commande.getStatus(),
                commande.getClient().getId());
    }
}

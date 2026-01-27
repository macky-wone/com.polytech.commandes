package com.polytech.commandes.com.polytech.commandes.service.impl;

import com.polytech.commandes.com.polytech.commandes.dto.LigneCommandeDTO;
import com.polytech.commandes.com.polytech.commandes.entity.Commande;
import com.polytech.commandes.com.polytech.commandes.entity.LigneCommande;
import com.polytech.commandes.com.polytech.commandes.entity.Produit;
import com.polytech.commandes.com.polytech.commandes.service.PolytechApiError;
import com.polytech.commandes.com.polytech.commandes.repository.CommandeRepository;
import com.polytech.commandes.com.polytech.commandes.repository.LigneCommandeRepository;
import com.polytech.commandes.com.polytech.commandes.repository.ProduitRepository;
import com.polytech.commandes.com.polytech.commandes.service.LigneCommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LigneCommandeServiceImpl implements LigneCommandeService {

    @Autowired
    private LigneCommandeRepository ligneCommandeRepository;

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Override
    public LigneCommandeDTO createLigneCommande(LigneCommandeDTO ligneCommandeDTO) {
        Commande commande = commandeRepository.findById(ligneCommandeDTO.getCommandeId())
                .orElseThrow(() -> new PolytechApiError(404, "Commande not found"));

        Produit produit = produitRepository.findById(ligneCommandeDTO.getProduitId())
                .orElseThrow(() -> new PolytechApiError(404, "Product not found"));

        // Check stock
        if (produit.getStock() < ligneCommandeDTO.getQuantite()) {
            throw new PolytechApiError(422, "Insufficient stock for product: " + produit.getNom());
        }

        LigneCommande ligneCommande = new LigneCommande(commande, produit, ligneCommandeDTO.getQuantite());
        LigneCommande savedLigneCommande = ligneCommandeRepository.save(ligneCommande);

        // Update product stock
        produit.setStock(produit.getStock() - ligneCommandeDTO.getQuantite());
        produitRepository.save(produit);

        // Add to commande
        commande.getLignes().add(savedLigneCommande);
        commandeRepository.save(commande);

        return mapToDTO(savedLigneCommande);
    }

    @Override
    public Optional<LigneCommandeDTO> getLigneCommandeById(Long id) {
        return ligneCommandeRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    public LigneCommandeDTO updateLigneCommande(Long id, LigneCommandeDTO ligneCommandeDTO) {
        LigneCommande ligneCommande = ligneCommandeRepository.findById(id)
                .orElseThrow(() -> new PolytechApiError(404, "Ligne Commande not found"));

        ligneCommande.setQuantite(ligneCommandeDTO.getQuantite());
        ligneCommande.setPrixUnitaire(ligneCommandeDTO.getPrixUnitaire());

        LigneCommande updatedLigneCommande = ligneCommandeRepository.save(ligneCommande);
        return mapToDTO(updatedLigneCommande);
    }

    @Override
    public void deleteLigneCommande(Long id) {
        ligneCommandeRepository.deleteById(id);
    }

    @Override
    public List<LigneCommandeDTO> getAllLignesCommande() {
        return ligneCommandeRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeDTO> getLignesCommandeByCommandeId(Long commandeId) {
        return ligneCommandeRepository.findByCommandeId(commandeId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private LigneCommandeDTO mapToDTO(LigneCommande ligneCommande) {
        return new LigneCommandeDTO(
                ligneCommande.getId(),
                ligneCommande.getCommande().getId(),
                ligneCommande.getProduit().getId(),
                ligneCommande.getQuantite(),
                ligneCommande.getPrixUnitaire());
    }
}

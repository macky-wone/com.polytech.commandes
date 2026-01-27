package com.polytech.commandes.com.polytech.commandes.controller;

import com.polytech.commandes.com.polytech.commandes.dto.LigneCommandeDTO;
import com.polytech.commandes.com.polytech.commandes.service.LigneCommandeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lignes-commande")
@CrossOrigin(origins = "*")
@Tag(name = "Lignes de Commande", description = "Gestion des lignes de commande")
public class LigneCommandeController {

    @Autowired
    private LigneCommandeService ligneCommandeService;

    @GetMapping
    @Operation(summary = "Récupérer toutes les lignes de commande")
    public ResponseEntity<List<LigneCommandeDTO>> getAllLignesCommande() {
        List<LigneCommandeDTO> lignesCommande = ligneCommandeService.getAllLignesCommande();
        return ResponseEntity.ok(lignesCommande);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une ligne de commande par ID")
    public ResponseEntity<LigneCommandeDTO> getLigneCommandeById(@PathVariable Long id) {
        Optional<LigneCommandeDTO> ligneCommande = ligneCommandeService.getLigneCommandeById(id);
        return ligneCommande.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/commande/{commandeId}")
    @Operation(summary = "Récupérer les lignes d'une commande")
    public ResponseEntity<List<LigneCommandeDTO>> getLignesCommandeByCommandeId(@PathVariable Long commandeId) {
        List<LigneCommandeDTO> lignesCommande = ligneCommandeService.getLignesCommandeByCommandeId(commandeId);
        return ResponseEntity.ok(lignesCommande);
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle ligne de commande")
    public ResponseEntity<LigneCommandeDTO> createLigneCommande(@RequestBody LigneCommandeDTO ligneCommandeDTO) {
        try {
            LigneCommandeDTO createdLigneCommande = ligneCommandeService.createLigneCommande(ligneCommandeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLigneCommande);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une ligne de commande")
    public ResponseEntity<LigneCommandeDTO> updateLigneCommande(@PathVariable Long id,
            @RequestBody LigneCommandeDTO ligneCommandeDTO) {
        try {
            LigneCommandeDTO updatedLigneCommande = ligneCommandeService.updateLigneCommande(id, ligneCommandeDTO);
            return ResponseEntity.ok(updatedLigneCommande);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une ligne de commande")
    public ResponseEntity<Void> deleteLigneCommande(@PathVariable Long id) {
        ligneCommandeService.deleteLigneCommande(id);
        return ResponseEntity.noContent().build();
    }
}

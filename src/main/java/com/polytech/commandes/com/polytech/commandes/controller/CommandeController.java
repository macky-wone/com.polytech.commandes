package com.polytech.commandes.com.polytech.commandes.controller;

import com.polytech.commandes.com.polytech.commandes.dto.CommandeDTO;
import com.polytech.commandes.com.polytech.commandes.entity.StatusCommande;
import com.polytech.commandes.com.polytech.commandes.service.CommandeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/commandes")
@CrossOrigin(origins = "*")
@Tag(name = "Commandes", description = "Gestion des commandes")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    @GetMapping
    @Operation(summary = "Récupérer toutes les commandes")
    public ResponseEntity<List<CommandeDTO>> getAllCommandes() {
        List<CommandeDTO> commandes = commandeService.getAllCommandes();
        return ResponseEntity.ok(commandes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une commande par ID")
    public ResponseEntity<CommandeDTO> getCommandeById(@PathVariable Long id) {
        Optional<CommandeDTO> commande = commandeService.getCommandeById(id);
        return commande.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/client/{clientId}")
    @Operation(summary = "Récupérer les commandes d'un client")
    public ResponseEntity<List<CommandeDTO>> getCommandesByClientId(@PathVariable Long clientId) {
        List<CommandeDTO> commandes = commandeService.getCommandesByClientId(clientId);
        return ResponseEntity.ok(commandes);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Récupérer les commandes par statut")
    public ResponseEntity<List<CommandeDTO>> getCommandesByStatus(@PathVariable StatusCommande status) {
        List<CommandeDTO> commandes = commandeService.getCommandesByStatus(status);
        return ResponseEntity.ok(commandes);
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle commande")
    public ResponseEntity<CommandeDTO> createCommande(@RequestBody CommandeDTO commandeDTO) {
        try {
            CommandeDTO createdCommande = commandeService.createCommande(commandeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCommande);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une commande")
    public ResponseEntity<CommandeDTO> updateCommande(@PathVariable Long id, @RequestBody CommandeDTO commandeDTO) {
        try {
            CommandeDTO updatedCommande = commandeService.updateCommande(id, commandeDTO);
            return ResponseEntity.ok(updatedCommande);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Mettre à jour le statut d'une commande")
    public ResponseEntity<CommandeDTO> updateCommandeStatus(@PathVariable Long id,
            @RequestParam StatusCommande status) {
        try {
            CommandeDTO updatedCommande = commandeService.updateCommandeStatus(id, status);
            return ResponseEntity.ok(updatedCommande);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une commande")
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id) {
        commandeService.deleteCommande(id);
        return ResponseEntity.noContent().build();
    }
}

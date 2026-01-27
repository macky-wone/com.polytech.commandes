package com.polytech.commandes.com.polytech.commandes.controller;

import com.polytech.commandes.com.polytech.commandes.dto.ProduitDTO;
import com.polytech.commandes.com.polytech.commandes.service.ProduitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produits")
@CrossOrigin(origins = "*")
@Tag(name = "Produits", description = "Gestion des produits")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @GetMapping
    @Operation(summary = "Récupérer tous les produits")
    public ResponseEntity<List<ProduitDTO>> getAllProduits() {
        List<ProduitDTO> produits = produitService.getAllProduits();
        return ResponseEntity.ok(produits);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un produit par ID")
    public ResponseEntity<ProduitDTO> getProduitById(@PathVariable Long id) {
        Optional<ProduitDTO> produit = produitService.getProduitById(id);
        return produit.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/nom/{nom}")
    @Operation(summary = "Récupérer un produit par nom")
    public ResponseEntity<ProduitDTO> getProduitByNom(@PathVariable String nom) {
        Optional<ProduitDTO> produit = produitService.getProduitByNom(nom);
        return produit.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau produit")
    public ResponseEntity<ProduitDTO> createProduit(@RequestBody ProduitDTO produitDTO) {
        try {
            ProduitDTO createdProduit = produitService.createProduit(produitDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduit);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un produit")
    public ResponseEntity<ProduitDTO> updateProduit(@PathVariable Long id, @RequestBody ProduitDTO produitDTO) {
        try {
            ProduitDTO updatedProduit = produitService.updateProduit(id, produitDTO);
            return ResponseEntity.ok(updatedProduit);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/stock")
    @Operation(summary = "Mettre à jour le stock d'un produit")
    public ResponseEntity<Void> updateStock(@PathVariable Long id, @RequestParam Integer quantite) {
        try {
            produitService.updateStock(id, quantite);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un produit")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build();
    }
}

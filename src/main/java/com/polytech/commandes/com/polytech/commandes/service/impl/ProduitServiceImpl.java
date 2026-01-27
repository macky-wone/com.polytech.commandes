package com.polytech.commandes.com.polytech.commandes.service.impl;

import com.polytech.commandes.com.polytech.commandes.dto.ProduitDTO;
import com.polytech.commandes.com.polytech.commandes.entity.Produit;
import com.polytech.commandes.com.polytech.commandes.repository.ProduitRepository;
import com.polytech.commandes.com.polytech.commandes.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProduitServiceImpl implements ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    @Override
    public ProduitDTO createProduit(ProduitDTO produitDTO) {
        if (produitRepository.existsByNom(produitDTO.getNom())) {
            throw new IllegalArgumentException("Product with this name already exists");
        }

        Produit produit = new Produit(produitDTO.getNom(), produitDTO.getPrix(), produitDTO.getStock());
        Produit savedProduit = produitRepository.save(produit);
        return mapToDTO(savedProduit);
    }

    @Override
    public Optional<ProduitDTO> getProduitById(Long id) {
        return produitRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    public ProduitDTO updateProduit(Long id, ProduitDTO produitDTO) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        produit.setNom(produitDTO.getNom());
        produit.setPrix(produitDTO.getPrix());
        produit.setStock(produitDTO.getStock());

        Produit updatedProduit = produitRepository.save(produit);
        return mapToDTO(updatedProduit);
    }

    @Override
    public void deleteProduit(Long id) {
        produitRepository.deleteById(id);
    }

    @Override
    public List<ProduitDTO> getAllProduits() {
        return produitRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProduitDTO> getProduitByNom(String nom) {
        return produitRepository.findByNom(nom).map(this::mapToDTO);
    }

    @Override
    public void updateStock(Long id, Integer quantite) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        int newStock = produit.getStock() - quantite;
        if (newStock < 0) {
            throw new IllegalArgumentException("Insufficient stock");
        }

        produit.setStock(newStock);
        produitRepository.save(produit);
    }

    private ProduitDTO mapToDTO(Produit produit) {
        return new ProduitDTO(produit.getId(), produit.getNom(), produit.getPrix(), produit.getStock());
    }
}

package com.polytech.commandes.com.polytech.commandes.dto;

import java.math.BigDecimal;

public class LigneCommandeDTO {
    private Long id;
    private Long commandeId;
    private Long produitId;
    private Integer quantite;
    private BigDecimal prixUnitaire;

    public LigneCommandeDTO() {
    }

    public LigneCommandeDTO(Long commandeId, Long produitId, Integer quantite) {
        this.commandeId = commandeId;
        this.produitId = produitId;
        this.quantite = quantite;
    }

    public LigneCommandeDTO(Long id, Long commandeId, Long produitId, Integer quantite, BigDecimal prixUnitaire) {
        this.id = id;
        this.commandeId = commandeId;
        this.produitId = produitId;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(Long commandeId) {
        this.commandeId = commandeId;
    }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public BigDecimal getSousTotal() {
        return prixUnitaire != null && quantite != null ? prixUnitaire.multiply(BigDecimal.valueOf(quantite))
                : BigDecimal.ZERO;
    }

    @Override
    public String toString() {
        return "LigneCommandeDTO{" +
                "id=" + id +
                ", commandeId=" + commandeId +
                ", produitId=" + produitId +
                ", quantite=" + quantite +
                ", prixUnitaire=" + prixUnitaire +
                '}';
    }
}

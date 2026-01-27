package com.polytech.commandes.com.polytech.commandes.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "lignes_commande")
public class LigneCommande {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;
    
    @Column(nullable = false)
    private Integer quantite;
    
    @Column(nullable = false)
    private BigDecimal prixUnitaire;
    
    // Constructeurs
    public LigneCommande() {}
    
    public LigneCommande(Commande commande, Produit produit, Integer quantite) {
        this.commande = commande;
        this.produit = produit;
        this.quantite = quantite;
        this.prixUnitaire = produit.getPrix();
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
    
    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
    
    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }
    
    public BigDecimal getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(BigDecimal prixUnitaire) { this.prixUnitaire = prixUnitaire; }
    
    // Méthode pour calculer le sous-total
    public BigDecimal getSousTotal() {
        return prixUnitaire.multiply(BigDecimal.valueOf(quantite));
    }
}
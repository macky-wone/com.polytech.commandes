package com.polytech.commandes.com.polytech.commandes.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produits")
public class Produit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal prix;
    
    @Column(nullable = false)
    private Integer stock;
    
    // Constructeurs
    public Produit() {}
    
    public Produit(String nom, BigDecimal prix, Integer stock) {
        this.nom = nom;
        this.prix = prix;
        this.stock = stock;
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public BigDecimal getPrix() { return prix; }
    public void setPrix(BigDecimal prix) { this.prix = prix; }
    
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}
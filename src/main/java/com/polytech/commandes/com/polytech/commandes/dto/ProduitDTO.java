package com.polytech.commandes.com.polytech.commandes.dto;

import java.math.BigDecimal;

public class ProduitDTO {
    private Long id;
    private String nom;
    private BigDecimal prix;
    private Integer stock;

    public ProduitDTO() {
    }

    public ProduitDTO(String nom, BigDecimal prix, Integer stock) {
        this.nom = nom;
        this.prix = prix;
        this.stock = stock;
    }

    public ProduitDTO(Long id, String nom, BigDecimal prix, Integer stock) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.stock = stock;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "ProduitDTO{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", stock=" + stock +
                '}';
    }
}

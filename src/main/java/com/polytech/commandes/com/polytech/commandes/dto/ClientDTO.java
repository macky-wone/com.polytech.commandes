package com.polytech.commandes.com.polytech.commandes.dto;

import java.util.List;

public class ClientDTO {
    private Long id;
    private String nom;
    private String email;
    private List<CommandeDTO> commandes;

    public ClientDTO() {
    }

    public ClientDTO(String nom, String email) {
        this.nom = nom;
        this.email = email;
    }

    public ClientDTO(Long id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CommandeDTO> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<CommandeDTO> commandes) {
        this.commandes = commandes;
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

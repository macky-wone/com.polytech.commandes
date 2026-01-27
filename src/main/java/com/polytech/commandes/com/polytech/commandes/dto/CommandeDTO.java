package com.polytech.commandes.com.polytech.commandes.dto;

import com.polytech.commandes.com.polytech.commandes.entity.StatusCommande;
import java.time.LocalDateTime;
import java.util.List;

public class CommandeDTO {
    private Long id;
    private LocalDateTime dateCommande;
    private StatusCommande status;
    private Long clientId;
    private List<LigneCommandeDTO> lignes;

    public CommandeDTO() {
    }

    public CommandeDTO(Long clientId) {
        this.clientId = clientId;
        this.dateCommande = LocalDateTime.now();
        this.status = StatusCommande.CREEE;
    }

    public CommandeDTO(Long id, LocalDateTime dateCommande, StatusCommande status, Long clientId) {
        this.id = id;
        this.dateCommande = dateCommande;
        this.status = status;
        this.clientId = clientId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }

    public StatusCommande getStatus() {
        return status;
    }

    public void setStatus(StatusCommande status) {
        this.status = status;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public List<LigneCommandeDTO> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneCommandeDTO> lignes) {
        this.lignes = lignes;
    }

    @Override
    public String toString() {
        return "CommandeDTO{" +
                "id=" + id +
                ", dateCommande=" + dateCommande +
                ", status=" + status +
                ", clientId=" + clientId +
                '}';
    }
}

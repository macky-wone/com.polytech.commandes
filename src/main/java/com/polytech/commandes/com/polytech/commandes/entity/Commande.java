package com.polytech.commandes.com.polytech.commandes.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

@Entity
@Table(name = "commandes")
public class Commande {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDateTime dateCommande;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCommande status;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LigneCommande> lignes = new ArrayList<>();
    
    // Constructeurs
    public Commande() {}
    
    public Commande(Client client) {
        this.dateCommande = LocalDateTime.now();
        this.status = StatusCommande.CREEE;
        this.client = client;
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public LocalDateTime getDateCommande() { return dateCommande; }
    public void setDateCommande(LocalDateTime dateCommande) { this.dateCommande = dateCommande; }
    
    public StatusCommande getStatus() { return status; }
    public void setStatus(StatusCommande status) { this.status = status; }
    
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    
    public List<LigneCommande> getLignes() { return lignes; }
    public void setLignes(List<LigneCommande> lignes) { this.lignes = lignes; }
    
    // Méthode utilitaire
    public void addLigne(LigneCommande ligne) {
        lignes.add(ligne);
        ligne.setCommande(this);
    }
    
    // Méthode pour calculer le total
    public BigDecimal getTotal() {
        return lignes.stream()
            .map(LigneCommande::getSousTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
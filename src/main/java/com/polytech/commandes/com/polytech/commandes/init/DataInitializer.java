package com.polytech.commandes.com.polytech.commandes.init;

import com.polytech.commandes.com.polytech.commandes.dto.ClientDTO;
import com.polytech.commandes.com.polytech.commandes.dto.CommandeDTO;
import com.polytech.commandes.com.polytech.commandes.dto.LigneCommandeDTO;
import com.polytech.commandes.com.polytech.commandes.dto.ProduitDTO;
import com.polytech.commandes.com.polytech.commandes.service.ClientService;
import com.polytech.commandes.com.polytech.commandes.service.CommandeService;
import com.polytech.commandes.com.polytech.commandes.service.LigneCommandeService;
import com.polytech.commandes.com.polytech.commandes.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Profile("dev")
public class DataInitializer implements CommandLineRunner {

        @Autowired
        private ClientService clientService;

        @Autowired
        private ProduitService produitService;

        @Autowired
        private CommandeService commandeService;

        @Autowired
        private LigneCommandeService ligneCommandeService;

        @Autowired
        private com.polytech.commandes.com.polytech.commandes.service.UserService userService;

        @Autowired
        private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

        @Override
        public void run(String... args) throws Exception {
                System.out.println("Initialisation des données (Profil DEV)...");

                // Créer des clients
                ClientDTO client1 = clientService.createClient(
                                new ClientDTO("Macky Mamadou WONE", "wone.macky@polytech.edu.sn"));
                System.out.println("Client 1 créé: " + client1.getNom());

                ClientDTO client2 = clientService.createClient(
                                new ClientDTO("Samba SIDIBE", "ssidibe@ept.edu.sn"));
                System.out.println("Client 2 créé: " + client2.getNom());

                ClientDTO client3 = clientService.createClient(
                                new ClientDTO("Dioxy DIOKHANE", "diokhane@polytech.edu.sn"));
                System.out.println("Client 3 créé: " + client3.getNom());

                // Créer des produits
                ProduitDTO produit1 = produitService.createProduit(
                                new ProduitDTO("Laptop Dell", new BigDecimal("850.00"), 5));
                System.out.println("Produit 1 créé: " + produit1.getNom() + " - Stock: " + produit1.getStock());

                ProduitDTO produit2 = produitService.createProduit(
                                new ProduitDTO("Souris Logitech", new BigDecimal("25.00"), 50));
                System.out.println("Produit 2 créé: " + produit2.getNom() + " - Stock: " + produit2.getStock());

                ProduitDTO produit3 = produitService.createProduit(
                                new ProduitDTO("Clavier Mécanique", new BigDecimal("120.00"), 15));
                System.out.println("Produit 3 créé: " + produit3.getNom() + " - Stock: " + produit3.getStock());

                // Créer une commande avec lignes
                CommandeDTO commande1 = commandeService.createCommande(
                                new CommandeDTO(client1.getId()));
                System.out.println("Commande 1 créée pour: " + client1.getNom());

                // Ajouter des lignes à la commande
                LigneCommandeDTO ligne1 = ligneCommandeService.createLigneCommande(
                                new LigneCommandeDTO(commande1.getId(), produit1.getId(), 1));
                System.out.println("Ligne 1 ajoutée - Produit: " + produit1.getNom() + " - Quantité: 1");

                LigneCommandeDTO ligne2 = ligneCommandeService.createLigneCommande(
                                new LigneCommandeDTO(commande1.getId(), produit2.getId(), 2));
                System.out.println("Ligne 2 ajoutée - Produit: " + produit2.getNom() + " - Quantité: 2");

                // Créer une deuxième commande
                CommandeDTO commande2 = commandeService.createCommande(
                                new CommandeDTO(client2.getId()));
                System.out.println("Commande 2 créée pour: " + client2.getNom());

                LigneCommandeDTO ligne3 = ligneCommandeService.createLigneCommande(
                                new LigneCommandeDTO(commande2.getId(), produit3.getId(), 3));
                System.out.println("Ligne 3 ajoutée - Produit: " + produit3.getNom() + " - Quantité: 3");

                System.out.println("Initialisation des données terminée !");
                System.out.println("\nRésumé:");
                System.out.println("   - 3 clients créés");
                System.out.println("   - 3 produits créés");
                System.out.println("   - 2 commandes avec 3 lignes créées");

                // Création d'un utilisateur par défaut pour l'authentification JWT
                try {
                        userService.loadUserByUsername("admin");
                } catch (org.springframework.security.core.userdetails.UsernameNotFoundException e) {
                        com.polytech.commandes.com.polytech.commandes.entity.AppUser user = new com.polytech.commandes.com.polytech.commandes.entity.AppUser(
                                        "admin", passwordEncoder.encode("admin123"), "ROLE_USER");
                        userService.save(user);
                        System.out.println("Utilisateur 'admin' créé (mot de passe admin123)");
                }
                System.out.println("\nSwagger UI disponible à: http://localhost:8080/swagger-ui.html");
        }
}

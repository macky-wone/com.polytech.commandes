package com.polytech.commandes.com.polytech.commandes.controller;

import com.polytech.commandes.com.polytech.commandes.dto.ClientDTO;
import com.polytech.commandes.com.polytech.commandes.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
@Tag(name = "Clients", description = "Gestion des clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    @Operation(summary = "Récupérer tous les clients", 
    description = "Retourne la liste de tous les clients")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un client par ID")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        Optional<ClientDTO> client = clientService.getClientById(id);
        return client.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Récupérer un client par email")
    public ResponseEntity<ClientDTO> getClientByEmail(@PathVariable String email) {
        Optional<ClientDTO> client = clientService.getClientByEmail(email);
        return client.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau client")
    @ApiResponse(responseCode = "201", description = "Client créé avec succès")
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {
        try {
            ClientDTO createdClient = clientService.createClient(clientDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un client")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
        try {
            ClientDTO updatedClient = clientService.updateClient(id, clientDTO);
            return ResponseEntity.ok(updatedClient);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un client")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}

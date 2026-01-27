package com.polytech.commandes.com.polytech.commandes.service;

import com.polytech.commandes.com.polytech.commandes.dto.ClientDTO;
import java.util.List;
import java.util.Optional;

public interface ClientService {
    ClientDTO createClient(ClientDTO clientDTO);

    Optional<ClientDTO> getClientById(Long id);

    ClientDTO updateClient(Long id, ClientDTO clientDTO);

    void deleteClient(Long id);

    List<ClientDTO> getAllClients();

    Optional<ClientDTO> getClientByEmail(String email);
}

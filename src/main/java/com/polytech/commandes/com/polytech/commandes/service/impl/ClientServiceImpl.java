package com.polytech.commandes.com.polytech.commandes.service.impl;

import com.polytech.commandes.com.polytech.commandes.dto.ClientDTO;
import com.polytech.commandes.com.polytech.commandes.entity.Client;
import com.polytech.commandes.com.polytech.commandes.repository.ClientRepository;
import com.polytech.commandes.com.polytech.commandes.service.ClientService;
import com.polytech.commandes.com.polytech.commandes.service.PolytechApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ClientDTO createClient(ClientDTO clientDTO) {
        if (clientRepository.existsByEmail(clientDTO.getEmail())) {
            throw new PolytechApiError(409, "Email already exists");
        }

        Client client = new Client(clientDTO.getNom(), clientDTO.getEmail());
        Client savedClient = clientRepository.save(client);
        return mapToDTO(savedClient);
    }

    @Override
    public Optional<ClientDTO> getClientById(Long id) {
        return clientRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new PolytechApiError(404, "Client not found"));

        client.setNom(clientDTO.getNom());
        client.setEmail(clientDTO.getEmail());

        Client updatedClient = clientRepository.save(client);
        return mapToDTO(updatedClient);
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClientDTO> getClientByEmail(String email) {
        return clientRepository.findByEmail(email).map(this::mapToDTO);
    }

    private ClientDTO mapToDTO(Client client) {
        return new ClientDTO(client.getId(), client.getNom(), client.getEmail());
    }
}

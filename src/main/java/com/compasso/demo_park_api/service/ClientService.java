package com.compasso.demo_park_api.service;

import com.compasso.demo_park_api.entity.Client;
import com.compasso.demo_park_api.exception.CpfUniqueViolationException;
import com.compasso.demo_park_api.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional
    public Client save(Client client) {
        try {
            return clientRepository.save(client);
        } catch (DataIntegrityViolationException e) {
            throw new CpfUniqueViolationException(String.format("CPF '%s' cannot be registered, already exists", client.getCpf()));
        }
    }
}

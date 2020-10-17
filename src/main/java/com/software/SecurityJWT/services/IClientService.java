package com.software.SecurityJWT.services;

import com.software.SecurityJWT.entities.Client;

import java.util.List;

public interface IClientService {
    public List<Client> findAll();
    public Client findById(Long id);
    public Client save(Client client);
    public void delete(Long id);
}

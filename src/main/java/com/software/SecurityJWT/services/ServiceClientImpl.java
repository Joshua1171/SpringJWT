package com.software.SecurityJWT.services;

import com.software.SecurityJWT.dao.IClientDao;
import com.software.SecurityJWT.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceClientImpl implements IClientService {


    @Autowired
    private IClientDao clientDao;

    @Override
    public List<Client> findAll() {
        return (List<Client>) clientDao.findAll();
    }

    @Override
    public Client findById(Long id) {
        return clientDao.findById(id).orElse(null);
    }

    @Override
    public Client save(Client client) {
        return clientDao.save(client);
    }

    @Override
    public void delete(Long id) {
        clientDao.deleteById(id);
    }
}

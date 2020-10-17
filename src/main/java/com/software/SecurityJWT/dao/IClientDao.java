package com.software.SecurityJWT.dao;

import com.software.SecurityJWT.entities.Client;
import org.springframework.data.repository.CrudRepository;

public interface IClientDao extends CrudRepository<Client,Long> {
}

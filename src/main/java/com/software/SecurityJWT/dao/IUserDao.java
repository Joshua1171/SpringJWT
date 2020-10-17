package com.software.SecurityJWT.dao;

import com.software.SecurityJWT.entities.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUserDao extends CrudRepository<Usuario,Long> {
    public Usuario findByUsername(String username);
}

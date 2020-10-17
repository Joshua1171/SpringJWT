package com.software.SecurityJWT.services;

import com.software.SecurityJWT.dao.IUserDao;
import com.software.SecurityJWT.entities.Role;
import com.software.SecurityJWT.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("jpaUserDetailService")
public class JpaUserDetailService implements UserDetailsService {


    @Autowired
    IUserDao userDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user=userDao.findByUsername(username);
        if (user==null){
            throw new UsernameNotFoundException("El usuario"+username+"no existe en el sistema");
        }
        List<GrantedAuthority> authorities=new ArrayList<>();
        for (Role role:user.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        if (authorities.isEmpty()){
            throw new UsernameNotFoundException("El usuario"+username+"no existe en el sistema");
        }

        return new User(user.getUsername(),user.getPassword(),user.getEnabled(),true,true,true,authorities);
    }
}

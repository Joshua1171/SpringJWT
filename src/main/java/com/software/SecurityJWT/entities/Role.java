package com.software.SecurityJWT.entities;

import javax.persistence.*;

@Entity
@Table(name = "authorities",uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","authority"} ))
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String authority;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}

package com.software.SecurityJWT.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "clientes")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotEmpty(message = "No puede ser vacio")
    @Size(min=4,max = 12,message = "El tamaño debe ser entre 4 y 12 caracteres")
    @Column(nullable = false)
    String name;
    @NotEmpty(message = "No puede ser vacio")
    String lastname;
    @NotEmpty(message = "No puede ser vacio")
    @Email(message = "No es un buen formato para un email")
    @Column(nullable = false,unique = true)
    String email;
    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    Date createAt;

    @PrePersist
    void prePersist(){
        createAt=new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String apellido) {
        this.lastname = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}

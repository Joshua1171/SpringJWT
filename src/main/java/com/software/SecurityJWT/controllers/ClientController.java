package com.software.SecurityJWT.controllers;


import com.software.SecurityJWT.entities.Client;
import com.software.SecurityJWT.services.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "http://localhost:5432")
@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @GetMapping("/clients")
    public List<Client> index(HttpServletRequest request){
        return clientService.findAll();
    }

    @GetMapping("/clients/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> show (@PathVariable Long id){
        Client client=null;
        Map<String,Object> response=new HashMap<>();
        try{
            client = clientService.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje","Failed to query the database");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(client == null) {
            response.put("mensaje", "ID del cliente: ".concat(id.toString().concat("no existe en la base de datos!")));
          return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Client>(client,HttpStatus.OK);
    }

    @PostMapping("/clients")
    ResponseEntity<?> create(@Valid @RequestBody Client client, BindingResult result) { //Valid aplica las validaciones

        Client newClient = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()) {

            // Mismo resultado que con lamda
            List<String> errors = new ArrayList<>();
            for(FieldError err: result.getFieldErrors()) {
                errors.add("El campo '" + err.getField() +"' "+ err.getDefaultMessage());
            }
           /* Lambda Java
           List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "The field '" + err.getField() +"' "+ err.getDefaultMessage())
                    .collect(Collectors.toList())
                    */
            response.put("errors", errors);
            new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            newClient = clientService.save(client);
        } catch(DataAccessException e) {
            response.put("mensaje", "No se pudo insertar en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El cliente fue creado satisfactoriamente!");
        response.put("client", newClient);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    @PutMapping("/clients/{id}")
    ResponseEntity<?> update(@Valid @RequestBody Client client, BindingResult result, @PathVariable Long id) {

        Client currentClient = clientService.findById(id);

        Client updatedClient = null;

        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for(FieldError err: result.getFieldErrors()) {
                errors.add("El campo '" + err.getField() +"' "+ err.getDefaultMessage());
            }

            /*List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "The field'" + err.getField() +"' "+ err.getDefaultMessage())
                    .collect(Collectors.toList())*/

            response.put("errors", errors);
           return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if ( currentClient == null) {
            response.put("mensaje", "Error:error al editar , ID de cliente "
                    .concat(id.toString().concat("no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            currentClient.setName(client.getName());
            currentClient.setLastname(client.getLastname());
            currentClient.setEmail(client.getEmail());
            currentClient.setCreateAt(client.getCreateAt());

            updatedClient = clientService.save(currentClient);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar al cliente en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
           return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "The client has been created successfully!");
        response.put("client", updatedClient);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    @DeleteMapping("/clients/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            clientService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eleminar al cliente");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "The client successfully removed!");

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }






    boolean hasRole(String role){
        SecurityContext context = SecurityContextHolder.getContext();
        if (context==null){
            return false;
        }
        Authentication auth = context.getAuthentication();
        if (auth==null){
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        return authorities.contains(new SimpleGrantedAuthority(role));
        /*for (GrantedAuthority authority:authorities){
            if(role.equals(authority.getAuthority())){
                logger.info("Hi".concat(auth.getName().concat("your role is".concat(authority.getAuthority()))))
                true
            }
        }*/
    }





}

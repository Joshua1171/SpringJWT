package com.software.SecurityJWT.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.software.SecurityJWT.auth.SimpleGrantedAuthorityMixin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Component
public class JWTServiceImpl implements JWTService {
    // SecretKey SECRET_KEY = new SecretKeySpec("Alguna.Clave.Secreta.123456".getBytes(), SignatureAlgorithm.HS384.getJcaName())

    //public static final String CLAVE_ENCODED = Base64Utils.encodeToString("Alguna.Clave.Secreta.123456".getBytes())
    SecretKey SECRET_KEY = Keys.hmacShaKeyFor("Alguna.Clave.Secreta.123456.Muy.Segura".getBytes());
    //public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512)
    public static final long EXPIRATION_DATE = 14000000L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    @Override
    public String create(Authentication auth) throws IOException {
        String username = ((User) auth.getPrincipal()).getUsername();
        Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
        Claims claims = Jwts.claims();
        claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
        String token = Jwts.builder().setClaims(claims).setSubject(username)
                .signWith(SECRET_KEY).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE)).compact();


        return token;
    }

    @Override
    public boolean validate(String token) {
        try {
            getClaims(token);
            return true;
        }catch (JwtException | IllegalArgumentException e){
            return false;
        }

    }

    @Override
    public Claims getClaims(String token) {
        Claims claims = null;
        try {
          claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build().parseClaimsJws(resolve(token)).getBody();

            return claims;
        } catch (JwtException | IllegalArgumentException e){
             e.printStackTrace();

        }

      return claims;
    }

    @Override
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    @Override
    public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
        Object roles = getClaims(token).get("authorities");

        Collection<? extends GrantedAuthority> authorities = Arrays
                .asList(new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
                        .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));

       return authorities;
    }

    @Override
    public String resolve(String token) {
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            return token.replace(TOKEN_PREFIX, "");
        }
        return "BAD_TOKEN";
    }
}

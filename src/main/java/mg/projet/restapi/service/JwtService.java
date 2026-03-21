package mg.projet.restapi.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import mg.projet.restapi.dto.UserDto;
import mg.projet.restapi.model.Role;

@Service
public class JwtService {
    private String secretKey = "3aurSb9?)g9+J)2A,+w2f_ItHKJsy9L4p]@nw^%PsJ?";

    private Key getKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    private Map<String, Object> userClaims(UserDto userDto){
        Map<String, Object> claims = new HashMap<>();

        claims.put("id", userDto.getId());
        claims.put("nom", userDto.getNom());
        claims.put("prenom", userDto.getPrenom());
        claims.put("telephone", userDto.getTelephone());
        claims.put("etat", userDto.getEtat());
        claims.put("dateentree",userDto.getDateentree() != null ? userDto.getDateentree().getTime() : null);

        // Extract only role names, not the full Role object
        List<String> roleNames = userDto.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toList());
        claims.put("roles", roleNames);

        return claims;
    }

    @SuppressWarnings("deprecation")
    public UserDto extractUserFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        UserDto userDto = new UserDto();
        userDto.setId(claims.get("id", Long.class));
        userDto.setNom(claims.get("nom", String.class));
        userDto.setPrenom(claims.get("prenom", String.class));
        userDto.setTelephone(claims.get("telephone", String.class));
        userDto.setEtat(claims.get("etat", Integer.class));
        userDto.setMail(claims.getSubject());

        @SuppressWarnings("unchecked")
        List<String> roleNames = (List<String>) claims.get("roles", List.class);
        List<Role> roles = roleNames.stream()
                .map(name -> new Role(name))
                .collect(Collectors.toList());
        userDto.setRoles(roles);

        return userDto;
    }

    @SuppressWarnings("deprecation")
    public String generateToken(UserDto user, long milliseconds){
        Map<String, Object> claims = userClaims(user);
        return Jwts.builder()
            .setSubject(user.getMail())
            .setClaims(claims)
            .setIssuedAt(new Date())
            .setExpiration(
                new Date(System.currentTimeMillis() +milliseconds)
            ).signWith(getKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    @SuppressWarnings("deprecation")
    public  boolean isValid(String token) {
        try{
            Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token);

            return true;
        } catch (Exception e){
            return false;
        }
    }

    @SuppressWarnings("deprecation")
    public String extractSubject(String token){
        try {
            return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getPayload()
                .getSubject()
                .toString();

        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("deprecation")
    public String getClaim(String token, String claim){
        try {
            return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getPayload()
                .get(claim)
                .toString();

        } catch (Exception e) {
            return null;
        }
    }
}

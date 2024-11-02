package com.questions.questions.security.jwt;

import com.google.gson.Gson;
import com.questions.questions.dao.SecurityTokenDto;
import com.questions.questions.dao.Users;
import com.questions.questions.services.impl.UserServicesImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Log4j2
@Component
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    @Autowired
    private UserServicesImpl userServicesImpl;

    //Generar token de acceso
    public SecurityTokenDto generatedAccessToken(User user) {
        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        Users users = this.userServicesImpl.findByIdentification(user.getUsername());
        String json = new Gson().toJson(roles);

        String compact = Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
                .signWith(getSecretKey(), Jwts.SIG.HS256)
                .claim("roles", json)
                .claim("name", users.getNameAll())
                .compact();

        return SecurityTokenDto.builder()
                .token(compact)
                .fullName(users.getNameAll()).build();
    }

    //Obtener el username del token
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    //Obtener una sola clain
    public <T> T getClaim(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    //Obtener todos los claims del token
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //Validar token de acceso
    public boolean isTokenValid(String token){
        try{
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        }catch (ExpiredJwtException e) {
            log.error("token expired");
            return false;
        } catch (UnsupportedJwtException e) {
            log.error("token unsupported");
            return false;
        } catch (MalformedJwtException e) {
            log.error("token malformed");
            return false;
        } catch (SignatureException e) {
            log.error("bad signature");
            return false;
        } catch (IllegalArgumentException e) {
            log.error("illegal args");
            return false;
        }
    }


    //Obtener firma del token
    public SecretKey getSecretKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

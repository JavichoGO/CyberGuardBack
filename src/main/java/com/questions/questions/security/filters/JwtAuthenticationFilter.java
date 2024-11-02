package com.questions.questions.security.filters;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.questions.questions.dao.SecurityTokenDto;
import com.questions.questions.dao.Users;
import com.questions.questions.security.jwt.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JwtUtils jwtUtils;

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        Users users = null;
        String identification = "";
        String password = "";
        try {
            users = new ObjectMapper().readValue(request.getInputStream(), Users.class);
            identification = users.getIdentification();
            password = users.getPassword();
        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(identification, password);

        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        User user = (User) authResult.getPrincipal();
        SecurityTokenDto securityTokenDto = jwtUtils.generatedAccessToken(user);

        response.addHeader(HttpHeaders.AUTHORIZATION, securityTokenDto.getToken());
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("token", securityTokenDto.getToken());
        stringObjectMap.put("message", "Authenticacíon correcta");
        stringObjectMap.put("username", user.getUsername());
        stringObjectMap.put("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse(""));
        stringObjectMap.put("fullName", securityTokenDto.getFullName());

        response.getWriter().write(new ObjectMapper().writeValueAsString(stringObjectMap));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("message", "Error en la autenticacion");
        stringObjectMap.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(stringObjectMap));
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();
    }
}

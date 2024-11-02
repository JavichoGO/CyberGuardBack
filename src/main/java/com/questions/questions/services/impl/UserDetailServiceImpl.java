package com.questions.questions.services.impl;

import com.questions.questions.dao.Users;
import com.questions.questions.repository.UsersRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String identification) throws UsernameNotFoundException {
        Users users = this.usersRepository.findByIdentification(identification);

        if (Objects.isNull(users)){
            log.error("Usuario no existe = {}" , identification);
            throw new UsernameNotFoundException("Usuario no existe");
        }

        Collection<? extends GrantedAuthority> authorities = users.getRoles().stream()
                .map(s -> new SimpleGrantedAuthority("ROLE_".concat(s.getName()))).collect(Collectors.toSet());

        return new User(users.getIdentification(),
                users.getPassword(),
                users.isActive(),
                true,
                true,
                true, authorities);
    }
}

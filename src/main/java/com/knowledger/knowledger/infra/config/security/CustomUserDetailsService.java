package com.knowledger.knowledger.infra.config.security;

import com.knowledger.knowledger.infra.persistence.user.IUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository _iUserRepository;

    public CustomUserDetailsService(IUserRepository userRepository) {
        _iUserRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var userEntity = _iUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));

        var authority = new SimpleGrantedAuthority(userEntity.getRole().getName());

        return org.springframework.security.core.userdetails.User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .authorities(authority)
                .build();
    }

}
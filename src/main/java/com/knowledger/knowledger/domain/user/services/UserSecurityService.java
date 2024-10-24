package com.knowledger.knowledger.domain.user.services;

import com.knowledger.knowledger.commom.Util;
import com.knowledger.knowledger.infra.persistence.user.IUserRepository;
import com.knowledger.knowledger.infra.persistence.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService, IUserSecurityService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));

        return User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .roles("USER")
                .build();
    }
}

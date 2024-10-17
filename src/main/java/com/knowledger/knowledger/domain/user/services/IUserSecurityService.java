package com.knowledger.knowledger.domain.user.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface IUserSecurityService {
    public UserDetails loadUserByUsername(String email);
}

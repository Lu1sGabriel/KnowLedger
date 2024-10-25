package com.knowledger.knowledger.domain.user.factories;

import com.knowledger.knowledger.domain.user.User;
import com.knowledger.knowledger.domain.user.role.Role;
import com.knowledger.knowledger.domain.user.services.IUserPasswordService;
import com.knowledger.knowledger.infra.exceptions.BusinessException;
import com.knowledger.knowledger.infra.persistence.user.IUserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserFactory implements IUserFactory {

    private final IUserPasswordService _iUserPasswordService;
    private final IUserRepository _iUserRepository;

    public UserFactory(IUserPasswordService iUserPasswordService, IUserRepository iUserRepository) {
        _iUserPasswordService = iUserPasswordService;
        _iUserRepository = iUserRepository;
    }

    @Override
    public User create(String name, String email, String password, String confirmedPassword, Role role) {
        validate(password, confirmedPassword, email);
        var encodedPassword = _iUserPasswordService.encode(password);
        return new User(name, email, encodedPassword, role);
    }

    private void validate(String password, String confirmedPassword, String email) {

        var user = _iUserRepository.findByEmail(email);

        if (user.isPresent()) {
            throw new BusinessException("Usuário já cadastrado com o email: " + email, HttpStatus.CONFLICT);
        }

        _iUserPasswordService.validate(password, confirmedPassword);
    }

}
package com.knowledger.knowledger.domain.user.factories;

import com.knowledger.knowledger.domain.user.User;
import com.knowledger.knowledger.domain.user.role.Role;
import com.knowledger.knowledger.domain.user.services.IUserPasswordService;
import com.knowledger.knowledger.infra.exceptions.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class UserFactory implements IUserFactory {

    private final IUserPasswordService _iUserPasswordService;

    public UserFactory(IUserPasswordService iUserPasswordService) {
        _iUserPasswordService = iUserPasswordService;
    }

    @Override
    public User create(String name, String email, String password, String confirmedPassword, Role role) {
        validate(password, confirmedPassword);
        var encodedPassword = _iUserPasswordService.encode(password);
        return new User(name, email, encodedPassword, role);
    }

    private void validate(String password, String confirmedPassword) throws BusinessException {
        _iUserPasswordService.validate(password, confirmedPassword);
    }

}
package com.knowledger.knowledger.domain.user.factories;

import com.knowledger.knowledger.commom.Constants;
import com.knowledger.knowledger.commom.Util;
import com.knowledger.knowledger.commom.mapper.IMapper;
import com.knowledger.knowledger.domain.user.User;
import com.knowledger.knowledger.domain.user.role.Role;
import com.knowledger.knowledger.infra.persistence.user.UserEntity;
import com.knowledger.knowledger.infra.persistence.user.role.IRoleRepository;
import org.springframework.stereotype.Service;

@Service
public class UserFactory implements IUserFactory{

    @Override
    public User create(String name, String email, String password, String confirmedPassword, Role role) throws Exception {
        validatePassword(password, confirmedPassword);
        var encodedPassword = Util.encodePassword(password);
        return new User(name, email, encodedPassword, role);
    }

    private void validatePassword(String password, String confirmedPassword) throws Exception {
        if (!password.equals(confirmedPassword)) throw new Exception("As senhas não são iguais");

        var errorMessage = Util.passwordValidate(password);

        if (errorMessage != null){
            throw new Exception(errorMessage);
        }
    }

}

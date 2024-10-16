package com.knowledger.knowledger.domain.user.factories;

import com.knowledger.knowledger.domain.user.User;
import org.springframework.stereotype.Service;

@Service
public class UserFactory implements IUserFactory{

    @Override
    public User create(String name, String email, String password) {
        return new User(name, email, password);
    }
}

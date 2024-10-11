package com.knowledger.knowledger.domain.entities.user.factories;

import com.knowledger.knowledger.domain.entities.user.User;

public class UserFactory implements IUserFactory{


    @Override
    public User create(String name, String email, String password) {
        return new User(name, email, password);
    }
}

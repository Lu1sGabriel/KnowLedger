package com.knowledger.knowledger.domain.entities.user.factories;

import com.knowledger.knowledger.domain.entities.user.User;

public interface IUserFactory {

    User create(String name, String email, String password);

}

package com.knowledger.knowledger.domain.user.factories;

import com.knowledger.knowledger.domain.user.User;

public interface IUserFactory {

    User create(String name, String email, String password);

}

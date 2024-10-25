package com.knowledger.knowledger.domain.user.factories;

import com.knowledger.knowledger.domain.user.User;
import com.knowledger.knowledger.domain.user.role.Role;

public interface IUserFactory {

    User create(String name, String email, String password, String confirmedPassword, Role role);

}

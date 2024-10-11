package com.knowledger.knowledger.domain.entities.user.services;

import com.knowledger.knowledger.domain.entities.user.User;

public interface IUserChangePasswordService {

    void changePassword(User user, String token, String oldPassword, String newPassword, String confirmedNewPassword) throws Exception;

}

package com.knowledger.knowledger.domain.user.services;

import com.knowledger.knowledger.domain.user.User;

public interface IUserChangePasswordService {

    void changePassword(User user, String token, String oldPassword, String newPassword, String confirmedNewPassword);

}

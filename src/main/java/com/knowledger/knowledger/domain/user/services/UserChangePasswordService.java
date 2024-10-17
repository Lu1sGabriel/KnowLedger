package com.knowledger.knowledger.domain.user.services;

import com.knowledger.knowledger.commom.annotations.Password;
import com.knowledger.knowledger.domain.user.User;
import org.springframework.stereotype.Service;

@Service
public class UserChangePasswordService implements IUserChangePasswordService{

    @Override
    public void changePassword(User user, String token, String oldPassword, String newPassword, String confirmedNewPassword) throws Exception {
        ValidatePassword(newPassword, confirmedNewPassword);
        ChangePassword(user, token, newPassword);
    }

    private void ValidatePassword(@Password String newPassword, @Password String confirmedNewPassword) throws Exception {
        if (!(newPassword.equals(confirmedNewPassword))) throw new Exception("A nova senha e confimação de senha devem ser iguais");
    }

    private void ChangePassword(User user, String token, String newPassword) throws Exception {
        user.resetPassword(token, newPassword);
    }

}

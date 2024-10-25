package com.knowledger.knowledger.domain.user.services;

import com.knowledger.knowledger.domain.user.User;
import org.springframework.stereotype.Service;

@Service
public class UserChangePasswordService implements IUserChangePasswordService {

    private final IUserPasswordService _iUserPasswordService;

    public UserChangePasswordService(IUserPasswordService iUserPasswordService) {
        _iUserPasswordService = iUserPasswordService;
    }

    @Override
    public void changePassword(User user, String token, String oldPassword, String newPassword, String confirmedNewPassword) {
        ValidatePassword(newPassword, confirmedNewPassword);
        ChangePassword(user, token, newPassword);
    }

    private void ValidatePassword(String newPassword, String confirmedNewPassword) {
        _iUserPasswordService.validate(newPassword, confirmedNewPassword);
    }

    private void ChangePassword(User user, String token, String newPassword) {
        user.resetPassword(token, newPassword);
    }

}

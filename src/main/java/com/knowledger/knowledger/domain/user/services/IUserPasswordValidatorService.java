package com.knowledger.knowledger.domain.user.services;

public interface IUserPasswordValidatorService {

    String encodePassword(String password);

    String validatePassword(String password);

    boolean arePasswordsEqual(String password, String confirmPassword);

}
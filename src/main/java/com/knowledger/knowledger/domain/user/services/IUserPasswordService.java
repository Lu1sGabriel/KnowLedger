package com.knowledger.knowledger.domain.user.services;

public interface IUserPasswordService {

    String encode(String password);

    void validate(String password, String confirmPassword);

}
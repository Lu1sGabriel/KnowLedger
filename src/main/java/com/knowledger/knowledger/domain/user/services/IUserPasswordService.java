package com.knowledger.knowledger.domain.user.services;

import com.knowledger.knowledger.infra.exceptions.BusinessException;

public interface IUserPasswordService {

    String encode(String password) throws BusinessException;

    void validate(String password, String confirmPassword) throws BusinessException;

}
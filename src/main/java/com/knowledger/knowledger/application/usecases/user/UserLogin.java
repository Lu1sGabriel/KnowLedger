package com.knowledger.knowledger.application.usecases.user;

import com.knowledger.knowledger.commom.mapper.IMapperDTO;
import com.knowledger.knowledger.infra.controller.user.UserTokenAuthenticationDTO;
import com.knowledger.knowledger.infra.gateways.user.IUserGateway;
import org.springframework.stereotype.Component;

@Component
public class UserLogin {

    private final IUserGateway _iUserGateway;
    private final IMapperDTO<UserTokenAuthenticationDTO, Object> _iMapper;


    public UserLogin(IUserGateway iUserGateway, IMapperDTO<UserTokenAuthenticationDTO, Object> iMapper) {
        _iUserGateway = iUserGateway;
        _iMapper = iMapper;
    }

    public UserTokenAuthenticationDTO apply(String email, String password) {

        var result = _iUserGateway.login(email, password);
        return _iMapper.toDto(result);

    }

}

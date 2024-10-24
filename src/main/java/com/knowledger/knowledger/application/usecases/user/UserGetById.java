package com.knowledger.knowledger.application.usecases.user;

import com.knowledger.knowledger.commom.mapper.IMapperDTO;
import com.knowledger.knowledger.domain.user.User;
import com.knowledger.knowledger.infra.controller.user.UserDetailDTO;
import com.knowledger.knowledger.infra.gateways.user.IUserGateway;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserGetById {

    private final IUserGateway iUserGateway;
    private final IMapperDTO<UserDetailDTO, User> iMapper;

    public UserGetById(IUserGateway iUserGateway, IMapperDTO<UserDetailDTO, User> iMapper) {
        this.iUserGateway = iUserGateway;
        this.iMapper = iMapper;
    }

    public UserDetailDTO apply(UUID id) throws Exception {

        var user = iUserGateway.getById(id);
        return iMapper.toDto(user);

    }

}

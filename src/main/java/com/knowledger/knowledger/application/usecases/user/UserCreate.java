package com.knowledger.knowledger.application.usecases.user;

import com.knowledger.knowledger.infra.gateways.user.IUserGateway;
import com.knowledger.knowledger.commom.mapper.IMapper;
import com.knowledger.knowledger.domain.user.User;
import com.knowledger.knowledger.infra.controller.user.UserCreateDTO;
import com.knowledger.knowledger.infra.controller.user.UserDetailDTO;
import com.knowledger.knowledger.infra.persistence.user.UserEntity;

public class UserCreate {

    private final IUserGateway iUserGateway;
    private final IMapper<UserDetailDTO, UserEntity, User> iMapper;


    public UserCreate(IUserGateway iUserGateway, IMapper iMapper) {
        this.iUserGateway = iUserGateway;
        this.iMapper = iMapper;
    }

    public UserDetailDTO apply(UserCreateDTO userCreateDTO) {

        var user = iUserGateway.create(userCreateDTO.name(), userCreateDTO.email(), userCreateDTO.password());
        return iMapper.toDto(user);

    }

}

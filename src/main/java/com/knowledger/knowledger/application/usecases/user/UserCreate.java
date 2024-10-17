package com.knowledger.knowledger.application.usecases.user;

import com.knowledger.knowledger.commom.mapper.IMapperDTO;
import com.knowledger.knowledger.infra.gateways.user.IUserGateway;
import com.knowledger.knowledger.domain.user.User;
import com.knowledger.knowledger.infra.controller.user.UserCreateDTO;
import com.knowledger.knowledger.infra.controller.user.UserDetailDTO;
import org.springframework.stereotype.Component;

@Component
public class UserCreate {

    private final IUserGateway iUserGateway;
    private final IMapperDTO<UserDetailDTO, User> iMapper;

    public UserCreate(IUserGateway iUserGateway, IMapperDTO<UserDetailDTO, User> iMapper) {
        this.iUserGateway = iUserGateway;
        this.iMapper = iMapper;
    }

    public UserDetailDTO apply(UserCreateDTO dto) throws Exception {

        var user = iUserGateway.create(dto.name(), dto.email(), dto.password(), dto.confirmedPassword());
        return iMapper.toDto(user);

    }

}

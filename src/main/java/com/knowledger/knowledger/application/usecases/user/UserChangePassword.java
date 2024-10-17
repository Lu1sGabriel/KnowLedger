package com.knowledger.knowledger.application.usecases.user;

import com.knowledger.knowledger.commom.mapper.IMapperDTO;
import com.knowledger.knowledger.infra.gateways.user.IUserGateway;
import com.knowledger.knowledger.domain.user.User;
import com.knowledger.knowledger.infra.controller.user.UserChangePasswordDTO;
import com.knowledger.knowledger.infra.controller.user.UserCreateDTO;
import com.knowledger.knowledger.infra.controller.user.UserDetailDTO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class UserChangePassword {

    private final IUserGateway iUserGateway;
    private final IMapperDTO<UserDetailDTO, User> iMapper;

    public UserChangePassword(IUserGateway iUserGateway, IMapperDTO<UserDetailDTO, User> iMapper) {
        this.iUserGateway = iUserGateway;
        this.iMapper = iMapper;
    }

    public UserDetailDTO apply(UserCreateDTO userCreateDTO) {

        var user = iUserGateway.create(userCreateDTO.name(), userCreateDTO.email(), userCreateDTO.password());
        return iMapper.toDto(user);

    }

    public UserDetailDTO apply(UserChangePasswordDTO dto) throws Exception {

        var user = iUserGateway.changePassword(dto.userId(), dto.token(), dto.oldPassword(), dto.newPassword(), dto.confirmedNewPassword());
        return iMapper.toDto(user);

    }
}

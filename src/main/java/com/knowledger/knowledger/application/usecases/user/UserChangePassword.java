package com.knowledger.knowledger.application.usecases.user;

import com.knowledger.knowledger.commom.mapper.IMapperDTO;
import com.knowledger.knowledger.domain.user.User;
import com.knowledger.knowledger.infra.controller.user.UserChangePasswordDTO;
import com.knowledger.knowledger.infra.controller.user.UserDetailDTO;
import com.knowledger.knowledger.infra.gateways.user.IUserGateway;
import org.springframework.stereotype.Component;

@Component
public class UserChangePassword {

    private final IUserGateway iUserGateway;
    private final IMapperDTO<UserDetailDTO, User> iMapper;

    public UserChangePassword(IUserGateway iUserGateway, IMapperDTO<UserDetailDTO, User> iMapper) {
        this.iUserGateway = iUserGateway;
        this.iMapper = iMapper;
    }

    public UserDetailDTO apply(UserChangePasswordDTO dto) {
        var user = iUserGateway.changePassword(dto.getUserId(), dto.getToken(), dto.getOldPassword(), dto.getNewPassword(), dto.getConfirmedNewPassword());
        return iMapper.toDto(user);
    }

}
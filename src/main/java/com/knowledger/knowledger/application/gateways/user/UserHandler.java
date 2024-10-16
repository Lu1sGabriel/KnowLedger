package com.knowledger.knowledger.application.gateways.user;

import com.knowledger.knowledger.infra.gateways.user.IUserGateway;
import com.knowledger.knowledger.commom.mapper.IMapper;
import com.knowledger.knowledger.domain.user.User;
import com.knowledger.knowledger.domain.user.factories.IUserFactory;
import com.knowledger.knowledger.domain.user.services.IUserChangePasswordService;
import com.knowledger.knowledger.infra.controller.user.UserDetailDTO;
import com.knowledger.knowledger.infra.persistence.user.IUserRepository;
import com.knowledger.knowledger.infra.persistence.user.UserEntity;

import java.util.UUID;

public class UserHandler implements IUserGateway {

    private final IUserFactory iUserFactory;
    private final IUserRepository iUserRepository;
    private final IUserChangePasswordService iUserChangePasswordService;
    private final IMapper<UserDetailDTO, UserEntity, User> iMapper;

    public UserHandler(IUserFactory iUserFactory, IUserRepository iUserRepository, IUserChangePasswordService iUserChangePasswordService, IMapper<UserDetailDTO, UserEntity, User> iMapper) {
        this.iUserFactory = iUserFactory;
        this.iUserRepository = iUserRepository;
        this.iUserChangePasswordService = iUserChangePasswordService;
        this.iMapper = iMapper;
    }

    @Override
    public User create(String name, String email, String password) {
        var user = iUserFactory.create(name, email, password);
        iUserRepository.save(iMapper.toEntity(user));
        return user;
    }

    @Override
    public User changePassword(UUID userId, String token, String oldPassword, String newPassword, String confirmedNewPassword) throws Exception {
        var userEntity = iUserRepository.findById(userId);

        var user = iMapper.toDomain(userEntity.get());

        iUserChangePasswordService.changePassword(user, token, oldPassword, newPassword, confirmedNewPassword);

        return user;
    }
}

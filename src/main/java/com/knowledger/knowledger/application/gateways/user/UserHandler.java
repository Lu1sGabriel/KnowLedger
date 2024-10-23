package com.knowledger.knowledger.application.gateways.user;

import com.knowledger.knowledger.commom.Constants.UserRole;
import com.knowledger.knowledger.domain.user.role.Role;
import com.knowledger.knowledger.domain.user.services.IUserAuthenticationService;
import com.knowledger.knowledger.infra.gateways.user.IUserGateway;
import com.knowledger.knowledger.commom.mapper.IMapper;
import com.knowledger.knowledger.domain.user.User;
import com.knowledger.knowledger.domain.user.factories.IUserFactory;
import com.knowledger.knowledger.domain.user.services.IUserChangePasswordService;
import com.knowledger.knowledger.infra.persistence.user.IUserRepository;
import com.knowledger.knowledger.infra.persistence.user.UserEntity;
import com.knowledger.knowledger.infra.persistence.user.role.IRoleRepository;
import com.knowledger.knowledger.infra.persistence.user.role.RoleEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserHandler implements IUserGateway {

    private final IUserFactory _iUserFactory;
    private final IUserRepository _iUserRepository;
    private final IUserChangePasswordService _iUserChangePasswordService;
    private final IMapper<UserEntity, User> _iMapper;
    private final IMapper<RoleEntity, Role> _iMapperRole;
    private final IRoleRepository _iRoleRepository;
    private final IUserAuthenticationService _iUserAuthenticationService;


    public UserHandler(IUserFactory iUserFactory, IUserRepository iUserRepository, IUserChangePasswordService iUserChangePasswordService, IMapper<UserEntity, User> iMapper, IMapper<RoleEntity, Role> iMapperRole, IRoleRepository iRoleRepository, IUserAuthenticationService iUserAuthenticationService) {
        _iUserFactory = iUserFactory;
        _iUserRepository = iUserRepository;
        _iUserChangePasswordService = iUserChangePasswordService;
        _iMapper = iMapper;
        _iMapperRole = iMapperRole;
        _iRoleRepository = iRoleRepository;
        _iUserAuthenticationService = iUserAuthenticationService;
    }

    @Override
    public User register(String name, String email, String password, String confirmedPassword) throws Exception {
        var role = _iRoleRepository.findById(UserRole.USER);
        var user = _iUserFactory.create(name, email, password, confirmedPassword, _iMapperRole.toDomain(role.get()));
        _iUserRepository.save(_iMapper.toEntity(user));
        return user;
    }

    @Override
    public User changePassword(UUID userId, String token, String oldPassword, String newPassword, String confirmedNewPassword) throws Exception {
        var userEntity = _iUserRepository.findById(userId);

        var user = _iMapper.toDomain(userEntity.get());

        _iUserChangePasswordService.changePassword(user, token, oldPassword, newPassword, confirmedNewPassword);

        return user;
    }

    @Override
    public User getById(UUID id) throws Exception {
        return _iUserRepository
                .findById(id)
                .map(_iMapper::toDomain)
                .orElseThrow(() -> new Exception("Usuário inexistente"));    }

    @Override
    public void login(String email, String password) {

        _iUserAuthenticationService.authenticate(email, password);

    }
}

package com.knowledger.knowledger.application.gateways.user;

import com.knowledger.knowledger.commom.Constants.UserRole;
import com.knowledger.knowledger.commom.mapper.IMapper;
import com.knowledger.knowledger.domain.user.User;
import com.knowledger.knowledger.domain.user.factories.IUserFactory;
import com.knowledger.knowledger.domain.user.role.Role;
import com.knowledger.knowledger.domain.user.services.IUserAuthenticationService;
import com.knowledger.knowledger.domain.user.services.IUserChangePasswordService;
import com.knowledger.knowledger.infra.exceptions.BusinessException;
import com.knowledger.knowledger.infra.gateways.user.IUserGateway;
import com.knowledger.knowledger.infra.persistence.user.IUserRepository;
import com.knowledger.knowledger.infra.persistence.user.UserEntity;
import com.knowledger.knowledger.infra.persistence.user.role.IRoleRepository;
import com.knowledger.knowledger.infra.persistence.user.role.RoleEntity;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

    public UserHandler(IUserFactory iUserFactory, IUserRepository iUserRepository, IUserChangePasswordService iUserChangePasswordService,
                       IMapper<UserEntity, User> iMapper, IMapper<RoleEntity, Role> iMapperRole,
                       IRoleRepository iRoleRepository, IUserAuthenticationService iUserAuthenticationService) {
        _iUserFactory = iUserFactory;
        _iUserRepository = iUserRepository;
        _iUserChangePasswordService = iUserChangePasswordService;
        _iMapper = iMapper;
        _iMapperRole = iMapperRole;
        _iRoleRepository = iRoleRepository;
        _iUserAuthenticationService = iUserAuthenticationService;
    }

    @Override
    public User register(String name, String email, String password, String confirmedPassword) {
        var role = _iRoleRepository.findById(UserRole.USER)
                .orElseThrow(() -> new BusinessException("Não foi possível completar o registro. Por favor, entre em contato com o setor de TI.", HttpStatus.INTERNAL_SERVER_ERROR));
        var user = _iUserFactory.create(name, email, password, confirmedPassword, _iMapperRole.toDomain(role));
        _iUserRepository.save(_iMapper.toEntity(user));
        return user;
    }

    @Override
    public User changePassword(UUID userId, String token, String oldPassword, String newPassword, String confirmedNewPassword) {
        var userEntity = _iUserRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado. Verifique os dados e tente novamente.", HttpStatus.NOT_FOUND));
        var user = _iMapper.toDomain(userEntity);
        _iUserChangePasswordService.changePassword(user, token, oldPassword, newPassword, confirmedNewPassword);
        return user;
    }

    @Override
    public User getById(UUID id) {
        return _iUserRepository.findById(id).map(_iMapper::toDomain)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado. Por favor, entre em contato com o setor de TI.", HttpStatus.NOT_FOUND));
    }

    @Override
    public Map<String, String> login(String email, String password) {
        var token = _iUserAuthenticationService.authenticate(email, password);

        if (Objects.isNull(token) || token.isEmpty()) {
            throw new BusinessException("Falha na autenticação. Verifique suas credenciais e tente novamente.", HttpStatus.UNAUTHORIZED);
        }

        Map<String, String> objectToken = new HashMap<>();
        objectToken.put("token", token);
        return objectToken;
    }

}
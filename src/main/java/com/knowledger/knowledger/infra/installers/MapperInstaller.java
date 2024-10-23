package com.knowledger.knowledger.infra.installers;

import com.knowledger.knowledger.commom.mapper.Mapper;
import com.knowledger.knowledger.domain.user.User;
import com.knowledger.knowledger.domain.user.role.Role;
import com.knowledger.knowledger.infra.controller.user.UserRegisterDTO;
import com.knowledger.knowledger.infra.controller.user.UserDetailDTO;
import com.knowledger.knowledger.infra.controller.user.role.RoleDetailDTO;
import com.knowledger.knowledger.infra.persistence.user.UserEntity;
import com.knowledger.knowledger.infra.persistence.user.role.RoleEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MapperInstaller {

    //User
    @Bean
    @Primary
    public Mapper<UserDetailDTO, UserEntity, User> userDetailMapper() {
        return new Mapper<>(UserDetailDTO.class, UserEntity.class, User.class);
    }

    @Bean
    public Mapper<UserRegisterDTO, UserEntity, User> userRegisterMapper() {
        return new Mapper<>(UserRegisterDTO.class, UserEntity.class, User.class);
    }

    @Bean
    public Mapper<RoleDetailDTO, RoleEntity, Role> roleMapper() {
        return new Mapper<>(RoleDetailDTO.class, RoleEntity.class, Role.class);
    }


    //Post

}


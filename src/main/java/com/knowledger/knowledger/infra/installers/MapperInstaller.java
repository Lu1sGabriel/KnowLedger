package com.knowledger.knowledger.infra.installers;

import com.knowledger.knowledger.commom.mapper.Mapper;
import com.knowledger.knowledger.domain.department.Department;
import com.knowledger.knowledger.domain.user.User;
import com.knowledger.knowledger.infra.controller.department.DepartmentDetailDTO;
import com.knowledger.knowledger.infra.controller.user.UserCreateDTO;
import com.knowledger.knowledger.infra.controller.user.UserDetailDTO;
import com.knowledger.knowledger.infra.persistence.department.DepartmentEntity;
import com.knowledger.knowledger.infra.persistence.user.UserEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MapperInstaller {

    @Bean
    @Primary
    public Mapper<UserDetailDTO, UserEntity, User> userDetailMapper() {
        return new Mapper<>(UserDetailDTO.class, UserEntity.class, User.class);
    }

    @Bean
    public Mapper<UserCreateDTO, UserEntity, User> userCreateMapper() {
        return new Mapper<>(UserCreateDTO.class, UserEntity.class, User.class);
    }

    @Bean
    public Mapper<DepartmentDetailDTO, DepartmentEntity, Department> departmentDetailMapper() {
        return new Mapper<>(DepartmentDetailDTO.class, DepartmentEntity.class, Department.class);
    }

}
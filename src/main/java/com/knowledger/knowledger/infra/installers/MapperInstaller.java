package com.knowledger.knowledger.infra.installers;

import com.knowledger.knowledger.commom.mapper.Mapper;
import com.knowledger.knowledger.domain.comment.Comment;
import com.knowledger.knowledger.domain.post.Post;
import com.knowledger.knowledger.domain.post.department.Department;
import com.knowledger.knowledger.domain.post.postType.PostType;
import com.knowledger.knowledger.domain.user.User;
import com.knowledger.knowledger.domain.user.role.Role;
import com.knowledger.knowledger.infra.controller.comment.CommentDetailDTO;
import com.knowledger.knowledger.infra.controller.comment.CommentRegisterDTO;
import com.knowledger.knowledger.infra.controller.department.DepartmentDetailDTO;
import com.knowledger.knowledger.infra.controller.post.PostDetailDTO;
import com.knowledger.knowledger.infra.controller.post.PostDetailDTO.CommentSummaryDTO;
import com.knowledger.knowledger.infra.controller.post.PostRegisterDTO;
import com.knowledger.knowledger.infra.controller.postType.PostTypeDetailDTO;
import com.knowledger.knowledger.infra.controller.user.UserDetailDTO;
import com.knowledger.knowledger.infra.controller.user.UserRegisterDTO;
import com.knowledger.knowledger.infra.controller.user.UserTokenAuthenticationDTO;
import com.knowledger.knowledger.infra.controller.user.role.RoleDetailDTO;
import com.knowledger.knowledger.infra.persistence.comment.CommentEntity;
import com.knowledger.knowledger.infra.persistence.department.DepartmentEntity;
import com.knowledger.knowledger.infra.persistence.post.PostEntity;
import com.knowledger.knowledger.infra.persistence.postType.PostTypeEntity;
import com.knowledger.knowledger.infra.persistence.user.UserEntity;
import com.knowledger.knowledger.infra.persistence.user.role.RoleEntity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MapperInstaller {

    // User
    @Bean
    @Primary
    public Mapper<UserDetailDTO, UserEntity, User> userDetailMapper() {
        return new Mapper<>(UserDetailDTO.class, UserEntity.class, User.class);
    }

    @Bean
    public Mapper<UserRegisterDTO, UserEntity, User> userRegisterMapper() {
        return new Mapper<>(UserRegisterDTO.class, UserEntity.class, User.class);
    }

    @SuppressWarnings("rawtypes")
    @Bean
    public Mapper<UserTokenAuthenticationDTO, Class, Object> TokenMapper() {
        return new Mapper<>(UserTokenAuthenticationDTO.class, Class.class, Object.class);
    }

    // Role
    @Bean
    public Mapper<RoleDetailDTO, RoleEntity, Role> roleMapper() {
        return new Mapper<>(RoleDetailDTO.class, RoleEntity.class, Role.class);
    }

    // Post

    @Bean
    @Primary
    public Mapper<PostDetailDTO, PostEntity, Post> postDetailMapper() {
        return new Mapper<>(PostDetailDTO.class, PostEntity.class, Post.class);
    }

    @Bean
    public Mapper<PostRegisterDTO, PostEntity, Post> postRegisterMapper() {
        return new Mapper<>(PostRegisterDTO.class, PostEntity.class, Post.class);
    }

    // Comment
    @Bean
    @Primary
    public Mapper<CommentDetailDTO, CommentEntity, Comment> commentDetailMapper() {
        return new Mapper<>(CommentDetailDTO.class, CommentEntity.class, Comment.class);
    }

    @Bean
    public Mapper<CommentRegisterDTO, CommentEntity, Comment> commentRegisterMapper() {
        return new Mapper<>(CommentRegisterDTO.class, CommentEntity.class, Comment.class);
    }

    @Bean
    public Mapper<CommentSummaryDTO, CommentEntity, Comment> commentSummaryMapper() {
        return new Mapper<>(CommentSummaryDTO.class, CommentEntity.class, Comment.class);
    }

    // Department
    @Bean
    public Mapper<DepartmentDetailDTO, DepartmentEntity, Department> departmentMapper() {
        return new Mapper<>(DepartmentDetailDTO.class, DepartmentEntity.class, Department.class);
    }

    // Post Type
    @Bean
    public Mapper<PostTypeDetailDTO, PostTypeEntity, PostType> postTypeMapper() {
        return new Mapper<>(PostTypeDetailDTO.class, PostTypeEntity.class, PostType.class);
    }

}
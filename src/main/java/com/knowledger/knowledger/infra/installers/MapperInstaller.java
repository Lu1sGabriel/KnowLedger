package com.knowledger.knowledger.infra.installers;

import com.knowledger.knowledger.commom.mapper.Mapper;
import com.knowledger.knowledger.commom.mapper.MappingConfigurer;
import com.knowledger.knowledger.domain.comment.Comment;
import com.knowledger.knowledger.domain.post.Post;
import com.knowledger.knowledger.domain.post.department.Department;
import com.knowledger.knowledger.domain.post.postImage.PostImage;
import com.knowledger.knowledger.domain.post.postType.PostType;
import com.knowledger.knowledger.domain.user.User;
import com.knowledger.knowledger.domain.user.role.Role;
import com.knowledger.knowledger.infra.controller.comment.CommentDetailDTO;
import com.knowledger.knowledger.infra.controller.comment.CommentRegisterDTO;
import com.knowledger.knowledger.infra.controller.department.DepartmentDetailDTO;
import com.knowledger.knowledger.infra.controller.post.PostDetailDTO;
import com.knowledger.knowledger.infra.controller.post.PostRegisterDTO;
import com.knowledger.knowledger.infra.controller.postImage.PostImageSendDTO;
import com.knowledger.knowledger.infra.controller.postType.PostTypeDetailDTO;
import com.knowledger.knowledger.infra.controller.user.UserDetailDTO;
import com.knowledger.knowledger.infra.controller.user.UserRegisterDTO;
import com.knowledger.knowledger.infra.controller.user.UserTokenAuthenticationDTO;
import com.knowledger.knowledger.infra.controller.user.role.RoleDetailDTO;
import com.knowledger.knowledger.infra.mappers.comment.CommentProjectionToCommentMappingConfigurer;
import com.knowledger.knowledger.infra.mappers.comment.CommentToCommentEntityMappingConfigurer;
import com.knowledger.knowledger.infra.mappers.post.PostProjectionToPostMappingConfigurer;
import com.knowledger.knowledger.infra.mappers.post.PostToPostDetailDTOMappingConfigurer;
import com.knowledger.knowledger.infra.mappers.post.PostToPostEntityMappingConfigurer;
import com.knowledger.knowledger.infra.persistence.comment.CommentEntity;
import com.knowledger.knowledger.infra.persistence.department.DepartmentEntity;
import com.knowledger.knowledger.infra.persistence.post.PostEntity;
import com.knowledger.knowledger.infra.persistence.post.PostProjection;
import com.knowledger.knowledger.infra.persistence.post.PostProjection.CommentProjection;
import com.knowledger.knowledger.infra.persistence.post.postImage.PostImageEntity;
import com.knowledger.knowledger.infra.persistence.post.postType.PostTypeEntity;
import com.knowledger.knowledger.infra.persistence.user.UserEntity;
import com.knowledger.knowledger.infra.persistence.user.role.RoleEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

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

        List<MappingConfigurer<?, ?>> configurers = new ArrayList<>();
        configurers.add(new PostToPostEntityMappingConfigurer());
        configurers.add(new PostToPostDetailDTOMappingConfigurer());
        configurers.add(new PostProjectionToPostMappingConfigurer());

        return new Mapper<>(PostDetailDTO.class, PostEntity.class,
                Post.class, configurers);
    }

    @Bean
    public Mapper<Void, PostProjection, Post> postMapper() {

        List<MappingConfigurer<?, ?>> configurers = new ArrayList<>();
        configurers.add(new PostProjectionToPostMappingConfigurer());

        return new Mapper<>(Void.class, PostProjection.class, Post.class, configurers);
    }

    @Bean
    public Mapper<PostRegisterDTO, PostEntity, Post> postRegisterMapper() {
        return new Mapper<>(PostRegisterDTO.class, PostEntity.class, Post.class);
    }

    // Comment
    @Bean
    @Primary
    public Mapper<CommentDetailDTO, CommentEntity, Comment> commentDetailMapper() {

        List<MappingConfigurer<?, ?>> configurers = new ArrayList<>();
        configurers.add(new CommentToCommentEntityMappingConfigurer());
        configurers.add(new CommentProjectionToCommentMappingConfigurer());

        return new Mapper<>(CommentDetailDTO.class, CommentEntity.class, Comment.class, configurers);
    }

    @Bean
    public Mapper<Void, CommentProjection, Comment> commentMapper() {
        List<MappingConfigurer<?, ?>> configurers = new ArrayList<>();
        configurers.add(new CommentProjectionToCommentMappingConfigurer());

        return new Mapper<>(Void.class, CommentProjection.class, Comment.class, configurers);
    }

    @Bean
    public Mapper<CommentRegisterDTO, CommentEntity, Comment> commentRegisterMapper() {
        return new Mapper<>(CommentRegisterDTO.class, CommentEntity.class, Comment.class);
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

    //  Post Image
    @Bean
    public Mapper<PostImageSendDTO, PostImageEntity, PostImage> postImageSendMapper() {
        return new Mapper<>(PostImageSendDTO.class, PostImageEntity.class, PostImage.class);
    }

}
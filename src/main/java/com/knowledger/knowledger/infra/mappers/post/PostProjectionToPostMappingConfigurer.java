package com.knowledger.knowledger.infra.mappers.post;

import org.modelmapper.ModelMapper;

import com.knowledger.knowledger.commom.mapper.MappingConfigurer;
import com.knowledger.knowledger.domain.post.Post;
import com.knowledger.knowledger.domain.user.User;
import com.knowledger.knowledger.infra.persistence.post.PostProjection;

public class PostProjectionToPostMappingConfigurer implements MappingConfigurer<PostProjection, Post> {

    @Override
    public void configure(ModelMapper modelMapper) {
        modelMapper.typeMap(PostProjection.class, Post.class).addMappings(mapper -> {
            mapper.map(PostProjection::getPostId, Post::setId);
            mapper.map(PostProjection::getTitle, Post::setTitle);
            mapper.map(PostProjection::getContent, Post::setContent);
            mapper.map(PostProjection::getPostStatusId, Post::setPostStatusId);
            mapper.map(PostProjection::getPostTypeId, Post::setPostTypeId);
            mapper.map(PostProjection::getCreatedAt, Post::setCreatedAt);
            mapper.map(PostProjection::getUpdatedAt, Post::setUpdatedAt);
            mapper.map(PostProjection::getDeletedAt, Post::setDeletedAt);
            mapper.map(PostProjection::getPublishedAt, Post::setPublishedAt);
        });

        modelMapper.typeMap(PostProjection.class, Post.class).setPostConverter(context -> {
            Post post = context.getDestination();
            PostProjection projection = context.getSource();

            User user = new User();
            user.setId(projection.getPostUserId());
            user.setName(projection.getPostUserName());
            post.setUser(user);

            return post;
        });
    }
}

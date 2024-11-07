package com.knowledger.knowledger.infra.mappers.post;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.knowledger.knowledger.commom.mapper.MappingConfigurer;
import com.knowledger.knowledger.domain.post.Post;
import com.knowledger.knowledger.infra.persistence.post.PostEntity;

public class PostToPostEntityMappingConfigurer implements MappingConfigurer<PostEntity, Post> {

    @Override
    public void configure(ModelMapper modelMapper) {
        modelMapper.addMappings(new PropertyMap<Post, PostEntity>() {
            @Override
            protected void configure() {
                map().setUserId(source.getUser().getId());
            }
        });
    }
}
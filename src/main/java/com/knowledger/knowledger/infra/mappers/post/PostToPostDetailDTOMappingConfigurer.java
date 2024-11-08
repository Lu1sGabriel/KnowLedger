package com.knowledger.knowledger.infra.mappers.post;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.knowledger.knowledger.commom.mapper.MappingConfigurer;
import com.knowledger.knowledger.domain.post.Post;
import com.knowledger.knowledger.infra.controller.post.PostDetailDTO;

public class PostToPostDetailDTOMappingConfigurer implements MappingConfigurer<Post, PostDetailDTO> {

    @Override
    public void configure(ModelMapper modelMapper) {
        modelMapper.addMappings(new PropertyMap<Post, PostDetailDTO>() {
            @Override
            protected void configure() {
                map(source.getUser().getId(), destination.getUser().getId());
                map(source.getUser().getName(), destination.getUser().getName());
            }
        });
    }
}
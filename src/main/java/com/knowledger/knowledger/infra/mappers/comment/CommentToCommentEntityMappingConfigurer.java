package com.knowledger.knowledger.infra.mappers.comment;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.knowledger.knowledger.commom.mapper.MappingConfigurer;
import com.knowledger.knowledger.domain.comment.Comment;
import com.knowledger.knowledger.infra.persistence.comment.CommentEntity;

public class CommentToCommentEntityMappingConfigurer implements MappingConfigurer<CommentEntity, Comment> {

    @Override
    public void configure(ModelMapper modelMapper) {
        modelMapper.addMappings(new PropertyMap<Comment, CommentEntity>() {
            @Override
            protected void configure() {
                map().setUserId(source.getUser().getId());
                map().setPostId(source.getPost().getId());
            }
        });
    }
}

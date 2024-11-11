package com.knowledger.knowledger.infra.mappers.comment;

import org.modelmapper.ModelMapper;
import org.modelmapper.Converter;

import com.knowledger.knowledger.commom.mapper.MappingConfigurer;
import com.knowledger.knowledger.domain.comment.Comment;
import com.knowledger.knowledger.domain.post.Post;
import com.knowledger.knowledger.domain.user.User;
import com.knowledger.knowledger.infra.persistence.post.PostProjection.CommentProjection;

public class CommentProjectionToCommentMappingConfigurer implements MappingConfigurer<CommentProjection, Comment> {

    @Override
    public void configure(ModelMapper modelMapper) {

        modelMapper.typeMap(CommentProjection.class, Comment.class).addMappings(mapper -> {
            mapper.map(CommentProjection::getId, Comment::setId);
            mapper.map(CommentProjection::getCommentId, Comment::setCommentId);
            mapper.map(CommentProjection::getContent, Comment::setContent);
            mapper.map(CommentProjection::getCommentStatusId, Comment::setCommentStatusId);
            mapper.map(CommentProjection::getCreatedAt, Comment::setCreatedAt);
            mapper.map(CommentProjection::getUpdatedAt, Comment::setUpdatedAt);
            mapper.map(CommentProjection::getDeletedAt, Comment::setDeletedAt);
            mapper.map(CommentProjection::getPublishedAt, Comment::setPublishedAt);

        });

        modelMapper.typeMap(CommentProjection.class, User.class).addMappings(mapper -> {
            mapper.map(CommentProjection::getUserName, User::setName);
            mapper.map(CommentProjection::getUserId, User::setId);
        });

        modelMapper.typeMap(CommentProjection.class, Post.class).addMappings(mapper -> {
            mapper.map(CommentProjection::getPostId, Post::setId);
        });

        Converter<CommentProjection, Comment> commentConverter = ctx -> {
            Comment comment = ctx.getDestination();

            User user = modelMapper.map(ctx.getSource(), User.class);
            comment.setUser(user);

            Post post = modelMapper.map(ctx.getSource(), Post.class);
            comment.setPost(post);

            return comment;
        };

        modelMapper.typeMap(CommentProjection.class, Comment.class).setPostConverter(commentConverter);
    }
}
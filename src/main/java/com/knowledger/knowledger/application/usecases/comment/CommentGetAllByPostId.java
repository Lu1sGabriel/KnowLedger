package com.knowledger.knowledger.application.usecases.comment;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.knowledger.knowledger.commom.mapper.IMapperDTO;
import com.knowledger.knowledger.domain.comment.Comment;
import com.knowledger.knowledger.infra.controller.comment.CommentDetailDTO;
import com.knowledger.knowledger.infra.gateways.comment.ICommentGateway;

@Component
public class CommentGetAllByPostId {

    private final ICommentGateway _ICommentGateway;
    private final IMapperDTO<CommentDetailDTO, Comment> _MapperDTO;

    public CommentGetAllByPostId(ICommentGateway iUserGateway, IMapperDTO<CommentDetailDTO, Comment> iMapper) {
        _ICommentGateway = iUserGateway;
        _MapperDTO = iMapper;
    }

    public List<CommentDetailDTO> apply(UUID postId) {
        var comment = _ICommentGateway.getAllByPostId(postId);
        return _MapperDTO.toDtoList(comment);
    }
}

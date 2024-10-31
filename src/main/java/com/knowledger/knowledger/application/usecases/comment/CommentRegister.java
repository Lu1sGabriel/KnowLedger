package com.knowledger.knowledger.application.usecases.comment;

import org.springframework.stereotype.Component;

import com.knowledger.knowledger.commom.mapper.IMapperDTO;
import com.knowledger.knowledger.domain.comment.Comment;
import com.knowledger.knowledger.infra.controller.comment.CommentDetailDTO;
import com.knowledger.knowledger.infra.controller.comment.CommentRegisterDTO;
import com.knowledger.knowledger.infra.gateways.comment.ICommentGateway;

@Component
public class CommentRegister {

    private final ICommentGateway _ICommentGateway;
    private final IMapperDTO<CommentDetailDTO, Comment> _MapperDTO;

    public CommentRegister(ICommentGateway iUserGateway, IMapperDTO<CommentDetailDTO, Comment> iMapper) {
        _ICommentGateway = iUserGateway;
        _MapperDTO = iMapper;
    }

    public CommentDetailDTO apply(CommentRegisterDTO dto) {
        var comment = _ICommentGateway.register(dto.getUserId(), dto.getPostId(), dto.getContent(), dto.getCommentId(),
                dto.getCommentStatusId());
        return _MapperDTO.toDto(comment);
    }
}

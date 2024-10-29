package com.knowledger.knowledger.infra.controller.comment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.knowledger.knowledger.application.usecases.comment.CommentRegister;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentRegister _CommentRegister;

    public CommentController(CommentRegister _CommentRegister) {
        this._CommentRegister = _CommentRegister;
    }

    @PostMapping("/register")
    public ResponseEntity<CommentDetailDTO> create(@RequestBody CommentRegisterDTO dto,
            UriComponentsBuilder uriBuilder) {
        var commentDetailDto = _CommentRegister.apply(dto);
        var uri = uriBuilder.path("/posts/{id}").buildAndExpand(commentDetailDto.getId()).toUri();
        return ResponseEntity.created(uri).body(commentDetailDto);
    }

}

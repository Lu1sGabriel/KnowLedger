package com.knowledger.knowledger.infra.controller.comment;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.knowledger.knowledger.application.usecases.comment.CommentGetAllByPostId;
import com.knowledger.knowledger.application.usecases.comment.CommentRegister;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comments")
@Validated
public class CommentController {

    private final CommentRegister _CommentRegister;
    private final CommentGetAllByPostId _CommentGetAllByPostId;

    public CommentController(CommentRegister commentRegister, CommentGetAllByPostId commentGetAllByPostId) {
        _CommentRegister = commentRegister;
        _CommentGetAllByPostId = commentGetAllByPostId;
    }

    @PostMapping("/register")
    public ResponseEntity<CommentDetailDTO> create(@Valid @RequestBody CommentRegisterDTO dto,
            UriComponentsBuilder uriBuilder) {
        var commentDetailDto = _CommentRegister.apply(dto);
        var uri = uriBuilder.path("/comments/{id}").buildAndExpand(commentDetailDto.getId()).toUri();
        return ResponseEntity.created(uri).body(commentDetailDto);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDetailDTO>> getAllByPostId(
            @PathVariable UUID postId,
            UriComponentsBuilder uriBuilder) {
        var commentDetailDto = _CommentGetAllByPostId.apply(postId);
        return ResponseEntity.ok(commentDetailDto);
    }

}

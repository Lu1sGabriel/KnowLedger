package com.knowledger.knowledger.infra.controller.post;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.knowledger.knowledger.application.usecases.post.PostGetAll;
import com.knowledger.knowledger.application.usecases.post.PostGetAllByUserId;
import com.knowledger.knowledger.application.usecases.post.PostGetById;
import com.knowledger.knowledger.application.usecases.post.PostRegister;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@Validated
public class PostController {

    private final PostRegister _postRegister;
    private final PostGetAll _PostGetAll;
    private final PostGetAllByUserId _PostGetAllByUserId;
    private final PostGetById _PostGetById;

    public PostController(PostRegister postRegister, PostGetAll postGetAll, PostGetAllByUserId postGetAllByUserId,
            PostGetById postGetById) {
        _postRegister = postRegister;
        _PostGetAll = postGetAll;
        _PostGetAllByUserId = postGetAllByUserId;
        _PostGetById = postGetById;
    }

    @PostMapping("/register")
    public ResponseEntity<PostDetailDTO> create(@Valid @RequestBody PostRegisterDTO dto,
            UriComponentsBuilder uriBuilder) {
        var postDetailDto = _postRegister.apply(dto);
        var uri = uriBuilder.path("/api/posts/{id}").buildAndExpand(postDetailDto.getId()).toUri();
        return ResponseEntity.created(uri).body(postDetailDto);
    }

    @GetMapping("")
    public ResponseEntity<Page<PostDetailDTO>> getAll(Pageable pageable) {
        var posts = _PostGetAll.apply(pageable);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDetailDTO> getById(@PathVariable UUID id) {
        var posts = _PostGetById.apply(id);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<PostDetailDTO>> getAllByUserId(@PathVariable UUID userId, Pageable pageable) {
        var posts = _PostGetAllByUserId.apply(userId, pageable);
        return ResponseEntity.ok(posts);
    }

}
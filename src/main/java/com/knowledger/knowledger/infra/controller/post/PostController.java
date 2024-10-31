package com.knowledger.knowledger.infra.controller.post;

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

import com.knowledger.knowledger.application.usecases.post.PostGetAll;
import com.knowledger.knowledger.application.usecases.post.PostGetAllByUserId;
import com.knowledger.knowledger.application.usecases.post.PostRegister;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@Validated
public class PostController {

    private final PostRegister _postRegister;
    private final PostGetAll _PostGetAll;
    private final PostGetAllByUserId _PostGetAllByUserId;

    public PostController(PostRegister postRegister, PostGetAll postGetAll, PostGetAllByUserId postGetAllByUserId) {
        _postRegister = postRegister;
        _PostGetAll = postGetAll;
        _PostGetAllByUserId = postGetAllByUserId;
    }

    @PostMapping("/register")
    public ResponseEntity<PostDetailDTO> create(@Valid @RequestBody PostRegisterDTO dto,
            UriComponentsBuilder uriBuilder) {
        var postDetailDto = _postRegister.apply(dto);
        var uri = uriBuilder.path("/posts/{id}").buildAndExpand(postDetailDto.getId()).toUri();
        return ResponseEntity.created(uri).body(postDetailDto);
    }

    @GetMapping("")
    public ResponseEntity<List<PostDetailDTO>> getAll() {
        var posts = _PostGetAll.apply();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDetailDTO>> getAllByUserId(@PathVariable UUID userId) {
        var posts = _PostGetAllByUserId.apply(userId);
        return ResponseEntity.ok(posts);
    }
}

package com.knowledger.knowledger.infra.controller.post;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.knowledger.knowledger.application.usecases.post.PostRegister;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostRegister _postRegister;

    public PostController(PostRegister postRegister) {
        _postRegister = postRegister;
    }

    @PostMapping("/register")
    public ResponseEntity<PostDetailDTO> create(@RequestBody PostRegisterDTO dto, UriComponentsBuilder uriBuilder) {
        var postDetailDto = _postRegister.apply(dto);
        var uri = uriBuilder.path("/posts/{id}").buildAndExpand(postDetailDto.getId()).toUri();
        return ResponseEntity.created(uri).body(postDetailDto);
    }
}

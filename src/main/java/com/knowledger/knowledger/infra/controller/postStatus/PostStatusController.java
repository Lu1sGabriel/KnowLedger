package com.knowledger.knowledger.infra.controller.postStatus;

import com.knowledger.knowledger.application.usecases.postStatus.PostStatusGetAll;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/postStatus")
public class PostStatusController {

    private final PostStatusGetAll _postStatusGetAll;

    public PostStatusController(PostStatusGetAll poPostStatusGetAll) {
        _postStatusGetAll = poPostStatusGetAll;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/getAll")
    public ResponseEntity<List<PostStatusDetailDTO>> getAll() {
        var postStatus = _postStatusGetAll.getAll();
        return ResponseEntity.ok().body(postStatus);
    }

}
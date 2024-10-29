package com.knowledger.knowledger.infra.controller.postType;

import com.knowledger.knowledger.application.usecases.postType.PostTypeGetAll;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/post-types")
public class PostTypeController {

    private final PostTypeGetAll _postTypeGetAll;

    public PostTypeController(PostTypeGetAll postTypeGetAll) {
        _postTypeGetAll = postTypeGetAll;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/getAll")
    public ResponseEntity<List<PostTypeDetailDTO>> getAll() {
        var postTypes = _postTypeGetAll.getAll();
        return ResponseEntity.ok(postTypes);
    }

}
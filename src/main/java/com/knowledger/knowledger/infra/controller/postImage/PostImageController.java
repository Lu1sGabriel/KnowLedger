package com.knowledger.knowledger.infra.controller.postImage;

import com.knowledger.knowledger.application.usecases.postImage.PostImageDelete;
import com.knowledger.knowledger.application.usecases.postImage.PostImageGet;
import com.knowledger.knowledger.application.usecases.postImage.PostImageSend;
import com.knowledger.knowledger.application.usecases.postImage.PostImageUpdate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/post-image")
public class PostImageController {
    private final PostImageSend postImageSend;
    private final PostImageUpdate postImageUpdate;
    private final PostImageDelete postImageDelete;
    private final PostImageGet postImageGet;

    public PostImageController(PostImageSend postImageSend, PostImageUpdate postImageUpdate, PostImageDelete postImageDelete, PostImageGet postImageGet) {
        this.postImageSend = postImageSend;
        this.postImageUpdate = postImageUpdate;
        this.postImageDelete = postImageDelete;
        this.postImageGet = postImageGet;
    }

    @GetMapping("/{postId}")
    public ResponseEntity<FileSystemResource> getImage(@PathVariable UUID postId) {
        File imageFile = postImageGet.get(postId);
        MediaType mediaType = determineMediaType(imageFile);
        FileSystemResource fileResource = new FileSystemResource(imageFile);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageFile.getName() + "\"")
                .body(fileResource);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@ModelAttribute PostImageSendDTO dto) {
        postImageSend.send(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@ModelAttribute PostImageSendDTO dto) {
        postImageUpdate.update(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(@PathVariable UUID postId) {
        postImageDelete.delete(postId);
        return ResponseEntity.ok().build();
    }

    private MediaType determineMediaType(File file) {
        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        }
        return MediaType.IMAGE_JPEG;
    }

}
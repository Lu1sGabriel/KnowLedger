package com.knowledger.knowledger.infra.controller.postImage;

import com.knowledger.knowledger.application.usecases.postImage.PostImageDelete;
import com.knowledger.knowledger.application.usecases.postImage.PostImageGet;
import com.knowledger.knowledger.application.usecases.postImage.PostImageSend;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/api/post-images")
public class PostImageController {
    private final PostImageSend postImageSend;
    private final PostImageDelete postImageDelete;
    private final PostImageGet postImageGet;

    public PostImageController(PostImageSend postImageSend, PostImageDelete postImageDelete, PostImageGet postImageGet) {
        this.postImageSend = postImageSend;
        this.postImageDelete = postImageDelete;
        this.postImageGet = postImageGet;
    }

    @PreAuthorize("hasRole('USER')")
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

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/send")
    public ResponseEntity<Void> send(@ModelAttribute PostImageSendDTO dto) {
        postImageSend.send(dto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(@PathVariable UUID postId) {
        postImageDelete.delete(postId);
        return ResponseEntity.ok().build();
    }

    private MediaType determineMediaType(File file) {
        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else if (fileName.endsWith(".pdf")) {
            return MediaType.APPLICATION_PDF;
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }

}
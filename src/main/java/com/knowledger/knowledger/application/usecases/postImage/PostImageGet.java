package com.knowledger.knowledger.application.usecases.postImage;

import com.knowledger.knowledger.infra.gateways.postImage.IPostImageGateway;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.UUID;

@Component
public class PostImageGet {
    private final IPostImageGateway _iPostImageGateway;

    public PostImageGet(IPostImageGateway iPostImageGateway) {
        _iPostImageGateway = iPostImageGateway;
    }

    public File get(UUID postId) {
        return _iPostImageGateway.getImage(postId);
    }

}
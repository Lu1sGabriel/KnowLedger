package com.knowledger.knowledger.application.usecases.postImage;

import com.knowledger.knowledger.infra.gateways.postImage.IPostImageGateway;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PostImageDelete {
    private final IPostImageGateway _iPostImageGateway;

    public PostImageDelete(IPostImageGateway iPostImageGateway) {
        _iPostImageGateway = iPostImageGateway;
    }

    public void delete(UUID postId) {
        _iPostImageGateway.deleteImage(postId);
    }

}
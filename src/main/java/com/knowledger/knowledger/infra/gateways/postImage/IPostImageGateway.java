package com.knowledger.knowledger.infra.gateways.postImage;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

public interface IPostImageGateway {
    File getImage(UUID postId);

    void saveImage(UUID postId, String url, InputStream file);

    void updateImage(UUID postId, String url, InputStream file);

    void deleteImage(UUID postId);

}
package com.knowledger.knowledger.application.gateways.postImage;

import com.knowledger.knowledger.commom.mapper.IMapper;
import com.knowledger.knowledger.commom.services.IFileUploadService;
import com.knowledger.knowledger.domain.post.postImage.PostImage;
import com.knowledger.knowledger.infra.exceptions.BusinessException;
import com.knowledger.knowledger.infra.gateways.postImage.IPostImageGateway;
import com.knowledger.knowledger.infra.persistence.post.postImage.IPostImageRepository;
import com.knowledger.knowledger.infra.persistence.post.postImage.PostImageEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

@Service
public class PostImageHandler implements IPostImageGateway {

    private final IFileUploadService _iFileUploadService;
    private final IPostImageRepository _iPostImageRepository;
    private final IMapper<PostImageEntity, PostImage> _iMapper;

    private final static String ERROR_MESSAGE = """
            A imagem associada ao post solicitado não foi encontrada. Verifique se o post existe, se o post possui uma imagem associada ou se a imagem já foi removida.
            """;

    public PostImageHandler(IFileUploadService fileUploadService, IPostImageRepository postImageRepository, IMapper<PostImageEntity, PostImage> mapper) {
        _iFileUploadService = fileUploadService;
        _iPostImageRepository = postImageRepository;
        _iMapper = mapper;
    }

    @Override
    public File getImage(UUID postId) {
        return _iFileUploadService.getFile(postId.toString());
    }

    @Override
    public void saveImage(UUID postId, String fileName, InputStream file) {
        var postImageDomain = new PostImage(postId);

        var savedPath = _iFileUploadService.upload(postId.toString(), file, fileName, postImageDomain.getCreatedAt());

        postImageDomain.updatePath(savedPath);
        var postImageEntity = _iMapper.toEntity(postImageDomain);

        _iPostImageRepository.save(postImageEntity);
    }

    @Override
    public void updateImage(UUID postId, String fileName, InputStream file) {
        var postImageEntity = _iPostImageRepository.findByPostId(postId)
                .orElseThrow(() -> new BusinessException(ERROR_MESSAGE, HttpStatus.NOT_FOUND));

        postImageEntity.markAsUpdated();
        var savedPath = _iFileUploadService.upload(postId.toString(), file, fileName, postImageEntity.getUpdatedAt());

        postImageEntity.updatePath(savedPath);
        _iPostImageRepository.save(postImageEntity);
    }

    @Override
    public void deleteImage(UUID postId) {
        var postImageEntity = _iPostImageRepository.findByPostId(postId)
                .orElseThrow(() -> new BusinessException(ERROR_MESSAGE, HttpStatus.NOT_FOUND));

        postImageEntity.markAsDeleted();

        _iPostImageRepository.save(postImageEntity);
    }

}
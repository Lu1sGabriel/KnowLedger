package com.knowledger.knowledger.application.usecases.postImage;

import com.knowledger.knowledger.infra.controller.postImage.PostImageSendDTO;
import com.knowledger.knowledger.infra.exceptions.BusinessException;
import com.knowledger.knowledger.infra.gateways.postImage.IPostImageGateway;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PostImageUpdate {
    private final IPostImageGateway _iPostImageGateway;

    public PostImageUpdate(IPostImageGateway iPostImageGateway) {
        _iPostImageGateway = iPostImageGateway;
    }

    public void update(PostImageSendDTO dto) {
        try {
            _iPostImageGateway.updateImage(dto.getPostId(), dto.getPath(), dto.getFile().getInputStream());
        } catch (IOException exception) {
            throw new BusinessException("Erro ao ler o arquivo de imagem. Por favor, entre em contato com o suporte de TI. ", exception, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
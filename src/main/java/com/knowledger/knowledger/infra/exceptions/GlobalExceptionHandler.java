package com.knowledger.knowledger.infra.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BusinessExceptionDTO> handleBusinessException(BusinessException exception) {
        var businessExceptionDTO = new BusinessExceptionDTO(exception.getMessage());
        return new ResponseEntity<>(businessExceptionDTO, exception.getStatus());
    }

    // Outros handlers para outras exceções podem ser adicionados aqui
}
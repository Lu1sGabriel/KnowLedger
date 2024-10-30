package com.knowledger.knowledger.infra.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // ERRO PADRAO
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BusinessExceptionDTO> handleException(Exception exception) {
        var businessExceptionDTO = new BusinessExceptionDTO(exception.getMessage());
        return new ResponseEntity<>(businessExceptionDTO, HttpStatus.BAD_REQUEST);
    }

    // ERRO DE REGRA DE NEGOCIO
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BusinessExceptionDTO> handleBusinessException(BusinessException exception) {
        var businessExceptionDTO = new BusinessExceptionDTO(exception.getMessage());
        return new ResponseEntity<>(businessExceptionDTO, exception.getStatus());
    }

    // ERRO DE ARGUMENTOS INVALIDOS
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
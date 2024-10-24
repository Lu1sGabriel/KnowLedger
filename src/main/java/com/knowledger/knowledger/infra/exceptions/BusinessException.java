package com.knowledger.knowledger.infra.exceptions;

public class BusinessException extends RuntimeException {
    public BusinessException() {
        super();
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message) {
        super(message);
    }

}
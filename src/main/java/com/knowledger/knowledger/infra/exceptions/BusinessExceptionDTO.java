package com.knowledger.knowledger.infra.exceptions;

public class BusinessExceptionDTO {
    private String error;

    public BusinessExceptionDTO() {
    }

    public BusinessExceptionDTO(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
package com.digital_guru.hackathonbackend.exception;

public class ApiException extends RuntimeException {
    protected String erroCode;

    public ApiException(String message, String erroCode){
        super(message);
        this.erroCode = erroCode;
    }
}

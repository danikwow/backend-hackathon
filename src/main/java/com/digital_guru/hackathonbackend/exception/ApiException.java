package com.digital_guru.hackathonbackend.exception;

/**
 * by Danil Koltovskikh at 26.03.2023
 */

public class ApiException extends RuntimeException {
    protected String erroCode;

    public ApiException(String message, String erroCode){
        super(message);
        this.erroCode = erroCode;
    }
}

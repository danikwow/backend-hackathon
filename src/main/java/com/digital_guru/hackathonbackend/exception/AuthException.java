package com.digital_guru.hackathonbackend.exception;

public class AuthException extends ApiException {

    public AuthException(String message, String erroCode) {
        super(message, erroCode);
    }
}

package com.digital_guru.hackathonbackend.exception;

/**
 * by Danil Koltovskikh at 26.03.2023
 */

public class AuthException extends ApiException {

    public AuthException(String message, String erroCode) {
        super(message, erroCode);
    }
}

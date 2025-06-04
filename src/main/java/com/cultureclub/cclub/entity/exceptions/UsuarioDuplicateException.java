package com.cultureclub.cclub.entity.exceptions;

public class UsuarioDuplicateException extends Exception {
    private static final long serialVersionUID = 1L;

    public UsuarioDuplicateException(String message) {
        super(message);
    }

    public UsuarioDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

}

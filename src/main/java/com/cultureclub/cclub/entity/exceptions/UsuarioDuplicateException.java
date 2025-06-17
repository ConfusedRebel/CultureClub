package com.cultureclub.cclub.entity.exceptions;

import java.io.Serial;

public class UsuarioDuplicateException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public UsuarioDuplicateException(String message) {
        super(message);
    }

    public UsuarioDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

}

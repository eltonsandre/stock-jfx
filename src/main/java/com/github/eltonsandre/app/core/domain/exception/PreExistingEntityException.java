package com.github.eltonsandre.app.core.domain.exception;

public class PreExistingEntityException extends Exception {
    public PreExistingEntityException(final String message,
            final Throwable cause) {
        super(message, cause);
    }

    public PreExistingEntityException(final String message) {
        super(message);
    }
}

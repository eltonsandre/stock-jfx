package com.github.eltonsandre.app.core.domain.exception;

public class NonExistentEntityException extends Exception {

    public NonExistentEntityException(final String message,
            final Throwable cause) {
        super(message, cause);
    }

    public NonExistentEntityException(final String message) {
        super(message);
    }

}

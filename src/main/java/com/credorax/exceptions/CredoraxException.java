package com.credorax.exceptions;

public class CredoraxException extends RuntimeException {

    public CredoraxException(Exception ex) {
        super(ex);
    }

    public CredoraxException(String message) {
        super(message);
    }

    public CredoraxException(String message, Throwable cause) {
        super(message, cause);
    }
}

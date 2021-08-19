package com.credorax.exceptions;

public class CredoraxException extends RuntimeException {

    public CredoraxException(Exception ex) {
        super(ex);
    }

}

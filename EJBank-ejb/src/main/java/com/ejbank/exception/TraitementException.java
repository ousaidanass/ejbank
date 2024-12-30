package com.ejbank.exception;

import java.util.Objects;

public class TraitementException extends Exception {
    private final ErrorIdentifier errorIdentifier;

    public TraitementException(ErrorIdentifier errorIdentifier) {
        this.errorIdentifier = Objects.requireNonNull(errorIdentifier);
    }

    public ErrorIdentifier getErrorIdentifier() {
        return errorIdentifier;
    }

    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}

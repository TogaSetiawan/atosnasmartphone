package com.atosnasmartphone.scheduler_service.exception;

public class ImportFailedException extends RuntimeException {
    public ImportFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImportFailedException(String message) {
        super(message);
    }
}

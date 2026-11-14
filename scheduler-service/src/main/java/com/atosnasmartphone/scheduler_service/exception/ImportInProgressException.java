package com.atosnasmartphone.scheduler_service.exception;

public class ImportInProgressException extends RuntimeException {
    public ImportInProgressException() {
        super("Impor sebelumnya masih berjalan");
    }
}

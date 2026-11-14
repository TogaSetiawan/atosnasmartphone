package com.atosnasmartphone.product_service.exception;

public class InternalAccessDeniedException extends RuntimeException {
    public InternalAccessDeniedException() {
        super("Endpoint internal tidak dapat diakses");
    }
}

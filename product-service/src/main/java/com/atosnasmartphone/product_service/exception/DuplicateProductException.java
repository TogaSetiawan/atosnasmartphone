package com.atosnasmartphone.product_service.exception;

public class DuplicateProductException extends RuntimeException {
    public DuplicateProductException(String sku) {
        super("skuProduct '" + sku + "' sudah digunakan produk lain");
    }
}

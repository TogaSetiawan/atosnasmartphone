package com.atosnasmartphone.product_service.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestDto {
    private String titleProduct;
    private String brandProduct;
    private String skuProduct;
    private String descProduct;
    private Integer stock;
    private BigDecimal price;
    private Double rating;
}

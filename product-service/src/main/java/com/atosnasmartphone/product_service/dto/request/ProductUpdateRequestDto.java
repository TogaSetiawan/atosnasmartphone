package com.atosnasmartphone.product_service.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateRequestDto {
    private Long id;
    private String titleProduct;
    private String brandProduct;
    private String skuProduct;
    private String descProduct;
    private Integer stock;
    private BigDecimal price;
    private Double rating;
}

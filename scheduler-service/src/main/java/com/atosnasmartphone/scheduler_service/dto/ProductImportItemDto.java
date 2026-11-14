package com.atosnasmartphone.scheduler_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImportItemDto {
    private String titleProduct;
    private String brandProduct;
    private String skuProduct;
    private String descProduct;
    private Integer stock;
    private BigDecimal price;
    private Double rating;
}

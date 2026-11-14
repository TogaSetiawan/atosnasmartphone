package com.atosnasmartphone.product_service.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ProductResponse {
    private Long id;
    private String titleProduct;
    private String brandProduct;
    private String skuProduct;
    private String descProduct;
    private Integer stock;
    private BigDecimal price;
    private Double rating;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}

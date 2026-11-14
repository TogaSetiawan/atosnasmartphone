package com.atosnasmartphone.scheduler_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DummyProductDto {
    private String title;
    private String brand;
    private String sku;
    private String description;
    private Integer stock;
    private BigDecimal price;
    private Double rating;
}

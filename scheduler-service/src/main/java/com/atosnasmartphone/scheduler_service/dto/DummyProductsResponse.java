package com.atosnasmartphone.scheduler_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DummyProductsResponse {
    private List<DummyProductDto> products;
    private Integer total;
}

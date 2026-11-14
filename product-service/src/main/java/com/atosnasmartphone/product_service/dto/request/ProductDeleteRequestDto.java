package com.atosnasmartphone.product_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDeleteRequestDto {

    @NotNull(message = "id wajib diisi")
    private Long id;
}

package com.atosnasmartphone.product_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductUpdateRequestDto extends ProductCreateRequestDto{

    @NotNull(message = "id wajib diisi")
    private Long id;
}

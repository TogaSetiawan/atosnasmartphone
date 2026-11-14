package com.atosnasmartphone.product_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductCreateRequestDto {

    @NotBlank(message = "titleProduct wajib diisi")
    @Size(max = 100, message = "titleProduct maksimal 100 karakter")
    private String titleProduct;

    @NotBlank(message = "brandProduct wajib diisi")
    @Size(max = 50, message = "brandProduct maksimal 50 karakter")
    private String brandProduct;

    @NotBlank(message = "skuProduct wajib diisi")
    @Size(max = 100, message = "skuProduct maksimal 100 karakter")
    private String skuProduct;

    @NotBlank(message = "descProduct wajib diisi")
    @Size(max = 500, message = "descProduct maksimal 500 karakter")
    private String descProduct;

    @NotNull(message = "stock wajib diisi")
    @Min(value = 0, message = "stock tidak boleh negatif")
    private Integer stock;

    @NotNull(message = "price wajib diisi")
    @DecimalMin(value = "0.00", message = "price tidak boleh negatif")
    @Digits(integer = 8, fraction = 2, message = "price maksimal 8 digit bulat dan 2 digit desimal")
    private BigDecimal price;

    @DecimalMin(value = "0.0", message = "rating minimal 0")
    @DecimalMax(value = "5.0", message = "rating maksimal 5")
    private Double rating;
}

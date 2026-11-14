package com.atosnasmartphone.product_service.controller;

import com.atosnasmartphone.product_service.dto.request.ProductCreateRequestDto;
import com.atosnasmartphone.product_service.dto.response.BaseResponse;
import com.atosnasmartphone.product_service.dto.response.ImportResultResponse;
import com.atosnasmartphone.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/internal/api/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InternalProductController {
    private final ProductService productService;

    @PostMapping("/import")
    public BaseResponse<ImportResultResponse> importProducts(@RequestBody List<ProductCreateRequestDto> items) {
        return BaseResponse.ok(productService.importProducts(items));
    }
}

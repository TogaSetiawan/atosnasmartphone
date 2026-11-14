package com.atosnasmartphone.product_service.controller;


import com.atosnasmartphone.product_service.dto.request.ProductDeleteRequestDto;
import com.atosnasmartphone.product_service.dto.request.ProductCreateRequestDto;
import com.atosnasmartphone.product_service.dto.request.ProductUpdateRequestDto;
import com.atosnasmartphone.product_service.dto.response.BaseResponse;
import com.atosnasmartphone.product_service.entity.ProductEntity;
import com.atosnasmartphone.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public BaseResponse<ProductEntity> create(@RequestBody ProductCreateRequestDto request) {
        return BaseResponse.ok(productService.create(request));
    }

    @PostMapping("/edit")
    public BaseResponse<ProductEntity> edit(@RequestBody ProductUpdateRequestDto request) {
        return BaseResponse.ok(productService.edit(request));
    }

    @GetMapping("/find")
    public BaseResponse<List<ProductEntity>> find(@RequestParam("title") String title) {
        return BaseResponse.ok(productService.findByTitle(title));
    }

    @PostMapping("/delete")
    public BaseResponse<String> delete(@RequestBody ProductDeleteRequestDto request) {
        return BaseResponse.ok(productService.delete(request));
    }
}

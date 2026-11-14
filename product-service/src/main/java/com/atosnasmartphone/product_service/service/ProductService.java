package com.atosnasmartphone.product_service.service;

import com.atosnasmartphone.product_service.dto.request.ProductDeleteRequestDto;
import com.atosnasmartphone.product_service.dto.request.ProductCreateRequestDto;
import com.atosnasmartphone.product_service.dto.request.ProductUpdateRequestDto;
import com.atosnasmartphone.product_service.dto.response.ProductResponse;
import com.atosnasmartphone.product_service.entity.ProductEntity;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductCreateRequestDto request);

    ProductResponse edit(ProductUpdateRequestDto request);

    List<ProductResponse> findByTitle(String title);

    void delete(Long id);

}

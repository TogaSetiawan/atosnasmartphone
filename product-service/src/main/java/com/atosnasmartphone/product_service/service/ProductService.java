package com.atosnasmartphone.product_service.service;

import com.atosnasmartphone.product_service.dto.request.ProductDeleteRequestDto;
import com.atosnasmartphone.product_service.dto.request.ProductRequestDto;
import com.atosnasmartphone.product_service.dto.request.ProductUpdateRequestDto;
import com.atosnasmartphone.product_service.entity.ProductEntity;

import java.util.List;

public interface ProductService {
    ProductEntity create(ProductRequestDto request);

    ProductEntity edit(ProductUpdateRequestDto request);

    List<ProductEntity> findByTitle(String title);

    String delete(ProductDeleteRequestDto request);
}

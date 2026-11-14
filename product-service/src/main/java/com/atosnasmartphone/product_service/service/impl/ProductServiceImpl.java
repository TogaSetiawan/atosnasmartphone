package com.atosnasmartphone.product_service.service.impl;

import com.atosnasmartphone.product_service.dto.request.ProductDeleteRequestDto;
import com.atosnasmartphone.product_service.dto.request.ProductCreateRequestDto;
import com.atosnasmartphone.product_service.dto.request.ProductUpdateRequestDto;
import com.atosnasmartphone.product_service.dto.response.ProductResponse;
import com.atosnasmartphone.product_service.entity.ProductEntity;
import com.atosnasmartphone.product_service.exception.DuplicateProductException;
import com.atosnasmartphone.product_service.exception.ProductNotFoundException;
import com.atosnasmartphone.product_service.repository.ProductRepository;
import com.atosnasmartphone.product_service.service.ProductService;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final Validator validator;


    @Override
    @Transactional
    public ProductResponse create(ProductCreateRequestDto request) {
        String sku = request.getSkuProduct().trim();
        if (productRepository.existsBySkuProduct(sku)) {
            throw new DuplicateProductException(sku);
        }

        ProductEntity entity = new ProductEntity();
        applyFieldsProduct(entity, request);
        entity.setCreatedDt(LocalDateTime.now());
        return toResponse(productRepository.saveAndFlush(entity));
    }

    @Override
    @Transactional
    public ProductResponse edit(ProductUpdateRequestDto request) {
        ProductEntity entity = productRepository.findById(request.getId())
                .orElseThrow(() -> new ProductNotFoundException(request.getId()));

        String sku = request.getSkuProduct().trim();
        productRepository.findBySkuProduct(sku)
                .filter(other -> !other.getId().equals(entity.getId()))
                .ifPresent(other -> {
                    throw new DuplicateProductException(sku);
                });

        applyFieldsProduct(entity, request);
        entity.setUpdatedDt(LocalDateTime.now());
        return toResponse(productRepository.saveAndFlush(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findByTitle(String title) {
        return productRepository.findByTitleLike(title.trim()).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(entity);
    }

    private void applyFieldsProduct(ProductEntity entity, ProductCreateRequestDto request) {
        entity.setTitleProduct(request.getTitleProduct().trim());
        entity.setBrandProduct(request.getBrandProduct().trim());
        entity.setSkuProduct(request.getSkuProduct().trim());
        entity.setDescProduct(request.getDescProduct().trim());
        entity.setStock(request.getStock());
        entity.setPrice(request.getPrice());
        entity.setRating(request.getRating());
    }

    private ProductResponse toResponse(ProductEntity e) {
        return ProductResponse.builder()
                .id(e.getId())
                .titleProduct(e.getTitleProduct())
                .brandProduct(e.getBrandProduct())
                .skuProduct(e.getSkuProduct())
                .descProduct(e.getDescProduct())
                .stock(e.getStock())
                .price(e.getPrice())
                .rating(e.getRating())
                .createdDate(e.getCreatedDt())
                .updatedDate(e.getUpdatedDt())
                .build();
    }

}

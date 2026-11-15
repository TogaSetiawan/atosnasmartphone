package com.atosnasmartphone.product_service.service.impl;

import com.atosnasmartphone.product_service.dto.request.ProductDeleteRequestDto;
import com.atosnasmartphone.product_service.dto.request.ProductCreateRequestDto;
import com.atosnasmartphone.product_service.dto.request.ProductUpdateRequestDto;
import com.atosnasmartphone.product_service.dto.response.ImportResultResponse;
import com.atosnasmartphone.product_service.dto.response.ProductResponse;
import com.atosnasmartphone.product_service.entity.ProductEntity;
import com.atosnasmartphone.product_service.exception.DuplicateProductException;
import com.atosnasmartphone.product_service.exception.ProductNotFoundException;
import com.atosnasmartphone.product_service.repository.ProductRepository;
import com.atosnasmartphone.product_service.service.ProductService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceImpl implements ProductService {


    private static final double IMPORT_MIN_RATING_EXCLUSIVE = 3.0;
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

    @Override
    public ImportResultResponse importProducts(List<ProductCreateRequestDto> items) {
        ImportResultResponse result = new ImportResultResponse();
        if (items == null) {
            return result;
        }
        result.setReceived(items.size());

        for (ProductCreateRequestDto item : items) {
            try {
                importOne(item, result);
            } catch (DataIntegrityViolationException e) {

                result.setSkippedDuplicate(result.getSkippedDuplicate() + 1);
            } catch (Exception e) {

                log.error("Gagal mengimpor sku={}: {}", item == null ? null : item.getSkuProduct(), e.getMessage());
                result.setFailed(result.getFailed() + 1);
            }
        }

        log.info("Impor selesai: {}", result);
        return result;
    }

    private void importOne(ProductCreateRequestDto item, ImportResultResponse result) {
        if (item == null) {
            result.setSkippedInvalid(result.getSkippedInvalid() + 1);
            return;
        }

        Set<ConstraintViolation<ProductCreateRequestDto>> violations = validator.validate(item);
        if (!violations.isEmpty()) {
            log.warn("Item impor dilewati (tidak valid) sku={}: {}", item.getSkuProduct(),
                    violations.stream().map(ConstraintViolation::getMessage).sorted().collect(Collectors.joining("; ")));
            result.setSkippedInvalid(result.getSkippedInvalid() + 1);
            return;
        }

        if (item.getRating() == null || item.getRating() <= IMPORT_MIN_RATING_EXCLUSIVE) {
            result.setSkippedLowRating(result.getSkippedLowRating() + 1);
            return;
        }

        String sku = item.getSkuProduct().trim();
        if (productRepository.existsBySkuProduct(sku)) {
            result.setSkippedDuplicate(result.getSkippedDuplicate() + 1);
            return;
        }



        ProductEntity entity = new ProductEntity();
        applyFieldsProduct(entity, item);
        entity.setCreatedDt(LocalDateTime.now());
        productRepository.saveAndFlush(entity);
        result.setImported(result.getImported() + 1);
    }


}

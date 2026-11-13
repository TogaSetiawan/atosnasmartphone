package com.atosnasmartphone.product_service.service.impl;

import com.atosnasmartphone.product_service.dto.request.ProductDeleteRequestDto;
import com.atosnasmartphone.product_service.dto.request.ProductRequestDto;
import com.atosnasmartphone.product_service.dto.request.ProductUpdateRequestDto;
import com.atosnasmartphone.product_service.entity.ProductEntity;
import com.atosnasmartphone.product_service.exception.DuplicateProductException;
import com.atosnasmartphone.product_service.exception.ProductNotFoundException;
import com.atosnasmartphone.product_service.repository.ProductRepository;
import com.atosnasmartphone.product_service.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductEntity create(ProductRequestDto request) {
        productRepository.findBySkuProduct(request.getSkuProduct()).ifPresent(p -> {
            throw new DuplicateProductException("SKU ini sudah ada: " + request.getSkuProduct());
        });

        ProductEntity entity = new ProductEntity();
        entity.setTitleProduct(request.getTitleProduct());
        entity.setBrandProduct(request.getBrandProduct());
        entity.setSkuProduct(request.getSkuProduct());
        entity.setDescProduct(request.getDescProduct());
        entity.setStock(request.getStock() != null ? request.getStock() : 0);
        entity.setPrice(request.getPrice());
        entity.setRating(request.getRating());
        entity.setCreatedDt(LocalDateTime.now());
        entity.setUpdatedDt(null);
        return productRepository.save(entity);
    }

    @Override
    public ProductEntity edit(ProductUpdateRequestDto request) {
        ProductEntity entity = productRepository.findById(request.getId())
                .orElseThrow(() -> new ProductNotFoundException("Produk tidak ditemukan: " + request.getId()));

        productRepository.findBySkuProduct(request.getSkuProduct()).ifPresent(existing -> {
            if (!existing.getId().equals(request.getId())) {
                throw new DuplicateProductException("SKU sudah ada: " + request.getSkuProduct());
            }
        });

        entity.setTitleProduct(request.getTitleProduct());
        entity.setBrandProduct(request.getBrandProduct());
        entity.setSkuProduct(request.getSkuProduct());
        entity.setDescProduct(request.getDescProduct());
        entity.setStock(request.getStock() != null ? request.getStock() : 0);
        entity.setPrice(request.getPrice());
        entity.setRating(request.getRating());
        entity.setUpdatedDt(LocalDateTime.now());
        return productRepository.save(entity);
    }

    @Override
    public List<ProductEntity> findByTitle(String title) {
        if (title == null) {
            title = "";
        }
        return productRepository.findByTitleProductContainingIgnoreCase(title);
    }

    @Override
    public String delete(ProductDeleteRequestDto request) {
        ProductEntity entity = productRepository.findById(request.getId())
                .orElseThrow(() -> new ProductNotFoundException("Produk tidak ditemukan: " + request.getId()));
        productRepository.delete(entity);
        return "Produk berhasil dihapus: " + request.getId();
    }

}

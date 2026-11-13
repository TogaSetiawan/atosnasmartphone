package com.atosnasmartphone.product_service.repository;

import com.atosnasmartphone.product_service.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByTitleProduct(String titleProduct);

    Optional<ProductEntity> findBySkuProduct(String skuProduct);

    List<ProductEntity> findByTitleProductContainingIgnoreCase(String titleProduct);
}

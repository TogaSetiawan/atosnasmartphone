package com.atosnasmartphone.product_service.repository;

import com.atosnasmartphone.product_service.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {


    Optional<ProductEntity> findBySkuProduct(String skuProduct);
    boolean existsBySkuProduct(String skuProduct);

    @Query("""
            select p from ProductEntity p
            where lower(p.titleProduct) like lower(concat('%', :title, '%'))
            order by p.id
            """)
    List<ProductEntity> findByTitleLike(@Param("title") String title);
}

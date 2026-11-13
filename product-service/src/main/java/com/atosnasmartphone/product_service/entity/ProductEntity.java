package com.atosnasmartphone.product_service.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "mst_product_smartphone")
@Data
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title_product", nullable = false, length = 100)
    private String titleProduct;

    @Column(name = "brand_product", nullable = false, length = 50)
    private String brandProduct;

    @Column(name = "sku_product", nullable = false, length = 100)
    private String skuProduct;

    //karena kategori udah specific smartphone, gantinya pake description aja.
    @Column(name = "desc_product", nullable = false, length = 500)
    private String descProduct;

    @Column(name = "stock", nullable = false, length = 500)
    private int stock;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "created_date")
    private LocalDateTime createdDt;

    @Column(name = "updated_date")
    private LocalDateTime updatedDt;

    @Column(name = "rating")
    private Double rating;
}

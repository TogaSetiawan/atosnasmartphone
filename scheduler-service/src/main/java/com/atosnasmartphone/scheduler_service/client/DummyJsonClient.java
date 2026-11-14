package com.atosnasmartphone.scheduler_service.client;


import com.atosnasmartphone.scheduler_service.dto.DummyProductsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "dummyjson", url = "${dummyjson.base-url}")
public interface DummyJsonClient {

    /** limit=0 meminta DummyJSON mengembalikan semua produk kategori (default-nya hanya 30). */
    @GetMapping("/products/category/{category}")
    DummyProductsResponse getByCategory(@PathVariable("category") String category,
                                        @RequestParam("limit") int limit);
}

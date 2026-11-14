package com.atosnasmartphone.scheduler_service.client;

import com.atosnasmartphone.scheduler_service.dto.BaseResponse;
import com.atosnasmartphone.scheduler_service.dto.ImportResultDto;
import com.atosnasmartphone.scheduler_service.dto.ProductImportItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "product-service", path = "/product")
public interface ProductClient {

    @PostMapping("/internal/api/v1/import")
    BaseResponse<ImportResultDto> importProducts(@RequestHeader("X-Internal-Key") String internalKey,
                                                 @RequestBody List<ProductImportItemDto> items);
}

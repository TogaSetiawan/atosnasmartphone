package com.atosnasmartphone.scheduler_service.controller;


import com.atosnasmartphone.scheduler_service.dto.BaseResponse;
import com.atosnasmartphone.scheduler_service.dto.ImportResultDto;
import com.atosnasmartphone.scheduler_service.service.ProductImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImportController {
    private final ProductImportService importService;

    @PostMapping("/import")
    public BaseResponse<ImportResultDto> triggerImport() {
        return BaseResponse.ok(importService.runImport());
    }

}

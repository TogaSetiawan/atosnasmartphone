package com.atosnasmartphone.scheduler_service.service;

import com.atosnasmartphone.scheduler_service.client.DummyJsonClient;
import com.atosnasmartphone.scheduler_service.client.ProductClient;
import com.atosnasmartphone.scheduler_service.dto.*;
import com.atosnasmartphone.scheduler_service.exception.ImportFailedException;
import com.atosnasmartphone.scheduler_service.exception.ImportInProgressException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductImportService {

    private final DummyJsonClient dummyJsonClient;
    private final ProductClient productClient;

    @Value("${dummyjson.category}")
    private String category;

    @Value("${internal.api-key}")
    private String internalApiKey;

    // Mencegah impor terjadwal dan impor manual berjalan bersamaan
    private final ReentrantLock lock = new ReentrantLock();


    public ImportResultDto runImport() {
        if (!lock.tryLock()) {
            throw new ImportInProgressException();
        }
        try {
            List<DummyProductDto> source = fetchFromDummyJson();
            if (source.isEmpty()) {
                log.info("DummyJSON tidak mengembalikan produk untuk kategori '{}'", category);
                return new ImportResultDto();
            }

            List<ProductImportItemDto> items = source.stream()
                    .filter(Objects::nonNull)
                    .map(this::toImportItem)
                    .toList();
            log.info("Mengirim {} produk '{}' ke product-service", items.size(), category);

            return sendToProductService(items);
        } finally {
            lock.unlock();
        }
    }

    private List<DummyProductDto> fetchFromDummyJson() {
        try {
            DummyProductsResponse response = dummyJsonClient.getByCategory(category, 0);
            if (response == null || response.getProducts() == null) {
                throw new ImportFailedException("Respons DummyJSON kosong atau tidak sesuai format");
            }
            return response.getProducts();
        } catch (ImportFailedException e) {
            throw e;
        } catch (Exception e) {
            throw new ImportFailedException("DummyJSON tidak dapat dihubungi: " + e.getMessage(), e);
        }
    }

    private ImportResultDto sendToProductService(List<ProductImportItemDto> items) {
        try {
            BaseResponse<ImportResultDto> response = productClient.importProducts(internalApiKey, items);
            if (response == null || response.getData() == null) {
                throw new ImportFailedException("Respons product-service kosong atau tidak sesuai format");
            }
            return response.getData();
        } catch (ImportFailedException e) {
            throw e;
        } catch (Exception e) {
            throw new ImportFailedException("product-service tidak dapat dihubungi: " + e.getMessage(), e);
        }
    }

    private ProductImportItemDto toImportItem(DummyProductDto p) {
        return new ProductImportItemDto(
                p.getTitle(),
                p.getBrand(),
                p.getSku(),
                p.getDescription(),
                p.getStock(),
                p.getPrice(),
                p.getRating());
    }
}

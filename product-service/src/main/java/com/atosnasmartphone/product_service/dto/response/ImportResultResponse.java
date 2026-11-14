package com.atosnasmartphone.product_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class ImportResultResponse {

// Ringkasan satu kali impor. received = imported + semua skipped* + failed

    private int received;
    private int imported;
    private int skippedLowRating;
    private int skippedDuplicate;
    private int skippedInvalid;
    private int failed;
}

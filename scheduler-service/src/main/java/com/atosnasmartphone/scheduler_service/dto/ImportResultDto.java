package com.atosnasmartphone.scheduler_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImportResultDto {
    private int received;
    private int imported;
    private int skippedLowRating;
    private int skippedDuplicate;
    private int skippedInvalid;
    private int failed;
}

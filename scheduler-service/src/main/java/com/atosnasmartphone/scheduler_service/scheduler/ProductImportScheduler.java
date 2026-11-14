package com.atosnasmartphone.scheduler_service.scheduler;

import com.atosnasmartphone.scheduler_service.dto.ImportResultDto;
import com.atosnasmartphone.scheduler_service.exception.ImportInProgressException;
import com.atosnasmartphone.scheduler_service.service.ProductImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductImportScheduler {

    private final ProductImportService importService;

    // Import awal
    @EventListener(ApplicationReadyEvent.class)
    public void importOnStartup() {
        execute("awal");
    }

    // Import Schedule Berikutnya
    @Scheduled(initialDelayString = "${scheduler.import.interval-ms}",
            fixedRateString = "${scheduler.import.interval-ms}")
    public void importScheduled() {
        execute("terjadwal");
    }

    // Execute gak lempar exception: failure hanya dicatat, schedule berikutnya tetap berjalan
    private void execute(String trigger) {
        try {
            ImportResultDto result = importService.runImport();
            log.info("Impor {} selesai: {}", trigger, result);
        } catch (ImportInProgressException e) {
            log.warn("Impor {} dilewati: {}", trigger, e.getMessage());
        } catch (Exception e) {
            log.error("Impor {} gagal: {}. Putaran berikutnya tetap berjalan.", trigger, e.getMessage());
            log.debug("Detail kegagalan impor {}", trigger, e);
        }
    }
}

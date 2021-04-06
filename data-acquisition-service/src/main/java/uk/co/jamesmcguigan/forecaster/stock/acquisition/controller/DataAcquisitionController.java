package uk.co.jamesmcguigan.forecaster.stock.acquisition.controller;

import javax.validation.constraints.NotNull;
import java.net.URI;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.co.jamesmcguigan.forecaster.stock.acquisition.DataAcquisitionServiceApplication;
import uk.co.jamesmcguigan.forecaster.stock.acquisition.job.JobService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/data-acquisition")
public class DataAcquisitionController {

    private static final String HISTORICAL_PRICES = "historicalPrices";
    private static final String TRENDS = "trends";
    private static final String DATA_ACQUISITION_JOB = "/data-acquisition/job/%s";
    private static final String DATA_RECEIVED_FOR_WITH_ID = "Data received for {} with id {}";
    @Autowired
    private JobService jobService;

    @PostMapping("/historical-prices/{id}")
    @ResponseBody
    public ResponseEntity postHistoricalPriceRequest(@PathVariable String id) {
        DataAcquisitionServiceApplication.logger.debug(DATA_RECEIVED_FOR_WITH_ID, HISTORICAL_PRICES, id);
        return createRequest(id, HISTORICAL_PRICES);
    }

    @PostMapping("/historical-prices/")
    @ResponseBody
    public ResponseEntity postHistoricalPriceRequest() {
        DataAcquisitionServiceApplication.logger.debug("Refresh all historical price data");
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT)
                .body(null);
    }

    private ResponseEntity<Object> createRequest(String id, String className) {
        Preconditions.checkNotNull(id);
        String jobId = checkResponseNotNull(jobService.createJob(id, className));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath(String.format(DATA_ACQUISITION_JOB, jobId))
                .build()
                .toUri();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .location(location)
                .body(null);
    }

    @NotNull
    private String checkResponseNotNull(String id) {
        if (Strings.isNullOrEmpty(id)) {
            throw new EntityException.JobNotFoundException();
        }
        return id;
    }

    @PostMapping("/trends/{id}")
    @ResponseBody
    public ResponseEntity postTrendRequest(@PathVariable String id) {
        DataAcquisitionServiceApplication.logger.debug(DATA_RECEIVED_FOR_WITH_ID, TRENDS, id);
        return createRequest(id, TRENDS);
    }

    @PostMapping("/trends/")
    @ResponseBody
    public ResponseEntity postTrendRequest() {
        DataAcquisitionServiceApplication.logger.debug("Refresh all historical price data");
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT)
                .body(null);
    }
}

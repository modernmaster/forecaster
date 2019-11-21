package uk.co.jamesmcguigan.forecaster.stock.acquisition.historicalprice;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;
import uk.co.jamesmcguigan.forecaster.stock.acquisition.Application;
import uk.co.jamesmcguigan.forecaster.stock.acquisition.historicalprice.request.RequestService;
import uk.co.jamesmcguigan.forecaster.stock.acquisition.historicalprice.request.Response;
import uk.co.jamesmcguigan.forecaster.stock.acquisition.job.Job;
import uk.co.jamesmcguigan.forecaster.stock.acquisition.job.JobService;
import uk.co.jamesmcguigan.forecaster.stock.acquisition.job.Status;

@AllArgsConstructor
@Service
public class HistoricalPriceService {

    private static final String UNABLE_TO_PROCESS_JOB_ID = "Unable to process job id:{}";
    private static final String UPDATING_JOB_S_TO_STATUS_S = "Updating job {} to status {}";
    private static final String HTTP_S_STOCK_S_FEATURE_S = "http://%s/api/stocks/%s";
    private static final String CREATING_REQUEST_FOR = "Creating request for {}";
    private static final String TRANSFORMING_TO_PRICE_FOR = "Transforming to price for {}";
    private static final String SENDING_OUT_PATCH_REQUEST_FOR_STOCK_SERVICE = "Sending out patch request for stock service {} to {}";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String PATCH_REQUEST_TO_STOCK_SERVICE_RETURNED = "Patch request to stock service returned {}";
    private RequestService requestService;
    private HistoricalPriceRepresentationTransformer historicalPriceRepresentationTransformer;
    private JobService jobService;

    public void processRequest(Job job) {
        try {
            String stockAPIAddress = System.getenv("STOCK_API_ADDR");
            String symbol = job.getEntityId();
            updateJobStatus(job, Status.PROCESSING);
            Application.logger.debug(CREATING_REQUEST_FOR, job.getId());
            Response response = requestService.makeRequest(symbol);
            Application.logger.debug(TRANSFORMING_TO_PRICE_FOR, job.getId());
            HistoricalPriceRepresentation historicalPriceRepresentation = historicalPriceRepresentationTransformer.transformFrom(response);
            URI requestUri = createUri(stockAPIAddress, job.getEntityClassifer(), job.getEntityId());
            Application.logger.debug(SENDING_OUT_PATCH_REQUEST_FOR_STOCK_SERVICE, job.getId(), requestUri.toString());
            makeRequest(requestUri, historicalPriceRepresentation);
            updateJobStatus(job, Status.COMPLETED);
        } catch (Exception e) {
            Application.logger.error(UNABLE_TO_PROCESS_JOB_ID, job.getId(), e);
            updateJobStatus(job, Status.FAIL);
        }
    }

    //TODO: TOO MANY ARGUMENTS!
    private URI createUri(String stockServiceUri, String entityClassifer, String entityId) {
        String uri = String.format(HTTP_S_STOCK_S_FEATURE_S, stockServiceUri, entityId, entityClassifer);
        return URI.create(uri);
    }

    private void makeRequest(URI requestUri, HistoricalPriceRepresentation historicalPriceRepresentation) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(historicalPriceRepresentation);
        HttpClient client = HttpClientBuilder
                .create()
                .build();
        HttpPatch httpPatch = new HttpPatch(requestUri);
        httpPatch.addHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
        httpPatch.setEntity(new StringEntity(json));
        HttpResponse response = client.execute(httpPatch);
        int statusCode = response.getStatusLine().getStatusCode();
        Application.logger.debug(PATCH_REQUEST_TO_STOCK_SERVICE_RETURNED, statusCode);
        if (HttpStatus.SC_NO_CONTENT!=statusCode) {
            throw new RequestException.BadRequestException();
        }
    }

    private void updateJobStatus(Job job, Status status) {
        Application.logger.debug(UPDATING_JOB_S_TO_STATUS_S, job.getId(), status);
        job.setStatus(status);
        job.setUpdated(LocalDateTime.now());
        jobService.save(job);
    }
}

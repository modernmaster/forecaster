package uk.co.jamesmcguigan.forecaster.stock.acquisition.historicalprice;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import uk.co.jamesmcguigan.forecaster.stock.acquisition.Application;
import uk.co.jamesmcguigan.forecaster.stock.acquisition.historicalprice.request.Response;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Component
public class HistoricalPriceRepresentationTransformer {

    private ObjectMapper objectMapper;

    HistoricalPriceRepresentation transformFrom(Response dataAcquisitionResponse) {
        List<PriceRepresentation> priceRepresentations;
        try {
            ResponseContentsWrapper responseContentsWrapper = objectMapper.readValue(dataAcquisitionResponse.getContents(), ResponseContentsWrapper.class);
            priceRepresentations =  responseContentsWrapper.getD()
                    .stream()
                    .map(x -> transformFrom(x))
                    .collect(toList());
        } catch (IOException e) {
            Application.logger.error("Unable to parse json response",e);
             priceRepresentations = Lists.newArrayList();
        }
        return new HistoricalPriceRepresentation(priceRepresentations);
    }

    private PriceRepresentation transformFrom(List<Double> values) {
        long epochMilliSeconds = Math.round(values.get(0));
        Date date = new Date(epochMilliSeconds);
        return new PriceRepresentation(
                date, values.get(1), values.get(2), values.get(3), values.get(4), new Date());
    }
}

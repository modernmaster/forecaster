package uk.co.jamesmcguigan.forecaster.stock.historicalprice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import uk.co.jamesmcguigan.forecaster.Application;
import uk.co.jamesmcguigan.forecaster.dataacquisition.DataAcquisitionResponse;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class PriceTransformer {

    private ObjectMapper objectMapper;

    List<Price> transformFrom(DataAcquisitionResponse dataAcquisitionResponse) {
        try {
            ResponseContentsWrapper responseContentsWrapper = objectMapper.readValue(dataAcquisitionResponse.getContents(), ResponseContentsWrapper.class);
            return responseContentsWrapper.getD()
                    .stream()
                    .map(x -> transformFrom(x))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            Application.logger.error("Unable to parse json response",e);
        }
        return Lists.newArrayList();
    }

    private Price transformFrom(List<Double> values) {
        Long epochMilliSeconds = Math.round(values.get(0));
        Date date = new Date(epochMilliSeconds);
        return new Price(
                date, values.get(1), values.get(2), values.get(3), values.get(4));
    }
}

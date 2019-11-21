package uk.co.jamesmcguigan.forecaster.stock;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;
import uk.co.jamesmcguigan.forecaster.StockServiceApplication;
import uk.co.jamesmcguigan.forecaster.stock.historicalprice.Price;
import uk.co.jamesmcguigan.forecaster.stock.liveprice.GoogleSheetRepresentation;
import uk.co.jamesmcguigan.forecaster.stock.trend.Trend;

@Component
public class StockTransformer {

    public List<Stock> transform(GoogleSheetRepresentation googleSheetRepresentation) {
        return googleSheetRepresentation.getValues().stream()
                .skip(1)
                .map(this::transform)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Stock transform(List<String> values) {
        try {
            List<Price> historicalPrices = Lists.newArrayList();
            Map<String, Trend> trends = Maps.newHashMap();
            return Stock.builder()
                    .id(values.get(2))
                    .admissionDate(values.get(0))
                    .companyName(values.get(1))
                    .symbol(values.get(2))
                    .icbIndustry(values.get(3))
                    .icbSuperSector(values.get(4))
                    .countryOfIncorporation(values.get(5))
                    .worldRegion(values.get(6))
                    .market(values.get(7))
                    .internationalIssuer(values.get(8))
                    .companyMarketCap(values.get(9))
                    .price(Double.parseDouble(values.get(10)))
                    .percentageChange(Double.parseDouble(values.get(11)))
                    .avgVolume(Integer.parseInt(values.get(12)))
                    .volume(Integer.parseInt(values.get(13)))
                    .pe(Double.parseDouble(values.get(14)))
                    .high52(Double.parseDouble(values.get(15)))
                    .low52(Double.parseDouble(values.get(16)))
                    .delay(values.get(17))
                    .historicalPrices(historicalPrices)
                    .trends(trends)
                    .dateTimeCreated(new Date())
                    .dateTimeUpdated(new Date())
                    .build();
        } catch (NumberFormatException ex) {
            StockServiceApplication.logger.info("Stock disabled: {}", values.get(1));
        }
        return null;
    }

}

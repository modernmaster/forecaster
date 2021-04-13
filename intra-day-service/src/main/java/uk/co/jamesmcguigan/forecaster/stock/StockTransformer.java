package uk.co.jamesmcguigan.forecaster.stock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uk.co.jamesmcguigan.forecaster.dao.GoogleSheetRepresentation;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
class StockTransformer {

    public static final String STOCK_DISABLED_CAUSE = "Stock disabled: {}: CAUSE: {}";

    public List<Stock> transform(GoogleSheetRepresentation googleSheetRepresentation) {
        return googleSheetRepresentation.getValues().stream()
                .skip(1)
                .map(this::transform)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Stock transform(List<String> values) {
        try {
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
                    .price(values.get(10))
                    .percentageChange(values.get(11))
                    .avgVolume(values.get(12))
                    .volume(values.get(13))
                    .pe(values.get(14))
                    .high52(values.get(15))
                    .low52(values.get(16))
                    .delay(values.get(17))
                    .dateTimeCreated(new Date())
                    .dateTimeUpdated(new Date())
                    .build();
        } catch (NumberFormatException ex) {
            log.info(Arrays.toString(values.toArray()));
            log.info(STOCK_DISABLED_CAUSE, values.get(1), ex.getMessage());
        }
        return null;
    }

    private Double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}

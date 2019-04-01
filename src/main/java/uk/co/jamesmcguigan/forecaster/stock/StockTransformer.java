package uk.co.jamesmcguigan.forecaster.stock;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;
import uk.co.jamesmcguigan.forecaster.stock.historicalprice.HistoricalPrice;
import uk.co.jamesmcguigan.forecaster.stock.historicalprice.Price;
import uk.co.jamesmcguigan.forecaster.stock.liveprice.GoogleSheetRepresentation;
import uk.co.jamesmcguigan.forecaster.stock.trend.Trend;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StockTransformer {

  public List<Stock> transform(GoogleSheetRepresentation googleSheetRepresentation) {
    return googleSheetRepresentation.getValues().stream()
            .skip(1)
            .map(this::transform)
            .collect(Collectors.toList());
  }

  private Stock transform(List<String> values) {
    List<Price> historicalPrices = Lists.newArrayList();
    Map<String,Trend> trends = Maps.newHashMap();
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
            .historicalPrices(historicalPrices)
            .trends(trends)
            .dateTimeCreated(new Date())
            .dateTimeUpdated(new Date())
            .build();
  }

}

package uk.co.jamesmcguigan.forecaster.stock;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StockTransformer {

  public List<Stock> transform(GoogleSheetRepresentation googleSheetRepresentation) {

    List<List<String>> values = googleSheetRepresentation.getValues();
    List<Stock> stocks = new ArrayList<>();
    for (List<String> value : values) {
      stocks.add(transform(value));
    }
    return stocks;
  }

  private Stock transform(List<String> values) {
    return new Stock(
        values.get(0),
        values.get(1),
        values.get(2),
        values.get(3),
        values.get(4),
        values.get(5),
        values.get(6),
        values.get(7),
        values.get(8),
        values.get(9),
        values.get(10),
        values.get(11),
        values.get(12),
        values.get(13),
        values.get(14),
        values.get(15),
        values.get(16),
        values.get(17));
  }

}

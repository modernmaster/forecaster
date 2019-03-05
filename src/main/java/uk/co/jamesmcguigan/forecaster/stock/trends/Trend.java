package uk.co.jamesmcguigan.forecaster.stock.trends;

import uk.co.jamesmcguigan.forecaster.stock.Stock;

import java.time.LocalDateTime;

//@Data
//@Entity
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Trend {

 // @Id
  private long id;
  private Stock stock;
  private TrendCategory trendCategory;
  private String trendValue;
  private LocalDateTime localDateTime;

  public Trend(Stock stock, TrendCategory trendCategory) {
    this.stock = stock;
    this.trendCategory = trendCategory;
    this.localDateTime = LocalDateTime.now();
  }
}

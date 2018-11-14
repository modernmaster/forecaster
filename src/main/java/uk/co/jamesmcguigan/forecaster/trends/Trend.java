package uk.co.jamesmcguigan.forecaster.trends;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import uk.co.jamesmcguigan.forecaster.stock.Stock;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Trend {

  @Id
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

package uk.co.jamesmcguigan.forecaster.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.springframework.data.annotation.Id;
import uk.co.jamesmcguigan.forecaster.stock.pattern.Pattern;
import uk.co.jamesmcguigan.forecaster.stock.price.HistoricalPrice;
import uk.co.jamesmcguigan.forecaster.stock.trend.Trend;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stock {
    @Id
    private String id;
    private String admissionDate;
    private String companyName;
    private String symbol;
    private String icbIndustry;
    private String icbSuperSector;
    private String countryOfIncorporation;
    private String worldRegion;
    private String market;
    private String internationalIssuer;
    private String companyMarketCap;
    @JsonDeserialize(using = DoubleConvertor.class)
    private Double price;
    @JsonDeserialize(using = DoubleConvertor.class)
    private Double percentageChange;
    private Integer volume;
    private Integer avgVolume;
    @JsonDeserialize(using = DoubleConvertor.class)
    private Double pe;
    @JsonDeserialize(using = DoubleConvertor.class)
    private Double high52;
    @JsonDeserialize(using = DoubleConvertor.class)
    private Double low52;
    private String delay;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<HistoricalPrice> historicalPrices;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Trend> trends;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Pattern> patterns;

    @Override
    public int hashCode() {
        return String.join("", getCompanyName(), getPrice().toString()).hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().equals(Stock.class)) {
            Stock stock = (Stock) obj;
            return getCompanyName().equals(stock.getCompanyName()) &&
                    getPrice().equals(stock.getPrice());
        }
        return false;
    }
}

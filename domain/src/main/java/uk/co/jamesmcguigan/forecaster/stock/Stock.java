package uk.co.jamesmcguigan.forecaster.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.springframework.data.annotation.Id;
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
    @NonNull
    private String id;
    @NonNull
    private String admissionDate;
    @NonNull
    private String companyName;
    @NonNull
    private String symbol;
    @NonNull
    private String icbIndustry;
    @NonNull
    private String icbSuperSector;
    @NonNull
    private String countryOfIncorporation;
    @NonNull
    private String worldRegion;
    @NonNull
    private String market;
    @NonNull
    private String internationalIssuer;
    @NonNull
    private String companyMarketCap;
    @NonNull

    @JsonDeserialize(using = DoubleConvertor.class)
    private Double price;
    @NonNull

    @JsonDeserialize(using = DoubleConvertor.class)
    private Double percentageChange;
    @NonNull
    private Integer volume;
    @NonNull
    private Integer avgVolume;

    @JsonDeserialize(using = DoubleConvertor.class)
    private Double pe;
    @NonNull

    @JsonDeserialize(using = DoubleConvertor.class)
    private Double high52;
    @NonNull

    @JsonDeserialize(using = DoubleConvertor.class)
    private Double low52;
    @NonNull
    private String delay;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<HistoricalPrice> historicalPrices;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Trend> trends;

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

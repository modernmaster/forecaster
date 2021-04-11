package uk.co.jamesmcguigan.forecaster.stock;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.co.jamesmcguigan.forecaster.stock.historicalprice.DateConverter;
import uk.co.jamesmcguigan.forecaster.stock.historicalprice.Price;
import uk.co.jamesmcguigan.forecaster.stock.trend.Trend;

//import uk.co.jamesmcguigan.uk.co.jamesmcguigan.forecaster.ui.forecaster.stock.historicalprice.Price;
//import javax.persistence.PrePersist;
//import javax.persistence.PreUpdate;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "stock")
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
    private String price;
    @NonNull
    @JsonDeserialize(using = DoubleConvertor.class)
    private String percentageChange;
    @NonNull
    private String volume;
    @NonNull
    private String avgVolume;
    @JsonDeserialize(using = DoubleConvertor.class)
    private String pe;
    @NonNull
    @JsonDeserialize(using = DoubleConvertor.class)
    private String high52;
    @NonNull
    @JsonDeserialize(using = DoubleConvertor.class)
    private String low52;
    @NonNull
    private String delay;
    @NonNull
    private List<Price> historicalPrices;
    @NonNull
    private Map<String, Trend> trends;
    @NonNull
    @JsonDeserialize(using = DateConverter.class)
    private Date dateTimeCreated;
    @NonNull
    @JsonDeserialize(using = DateConverter.class)
    private Date dateTimeUpdated;

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

   // @PrePersist
    void createdAt() {
        this.dateTimeCreated = new Date();
    }

    //@PreUpdate
    void updatedAt() {
        this.dateTimeUpdated = new Date();
    }
}

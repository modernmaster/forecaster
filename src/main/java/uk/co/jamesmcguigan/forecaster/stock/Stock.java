package uk.co.jamesmcguigan.forecaster.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.co.jamesmcguigan.forecaster.stock.historicalprice.Price;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@RequiredArgsConstructor
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
    private String price;
    @NonNull
    private String percentageChange;
    @NonNull
    private String volume;
    @NonNull
    private String avgVolume;
    @NonNull
    private String pe;
    @NonNull
    private String high52;
    @NonNull
    private String low52;
    @NonNull
    private String delay;
    @NonNull
    private List<Price> historicalPrices;
    @NonNull
    private Date dateTimeCreated;
    @NonNull
    private Date dateTimeUpdated;

    @Override
    public int hashCode() {
        return String.join("", getCompanyName(), getPrice()).hashCode();
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
    @PrePersist
    void createdAt() {
        this.dateTimeCreated = new Date();
    }

    @PreUpdate
    void updatedAt() {
        this.dateTimeUpdated = new Date();
    }
}

package uk.co.jamesmcguigan.forecaster.stock.historicalprice;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
//import org.springframework.data.annotation.Id;
import javax.persistence.*;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
@Data
@Document(collection = "price")
public class Price {

    @NonNull
    @JsonDeserialize(using = MongoDateConverter.class)
    private Date date;
    @NonNull
    @JsonDeserialize(using = PriceConverter.class)
    private Double closing;
    @NonNull
    @JsonDeserialize(using = PriceConverter.class)
    private Double opening;
    @NonNull
    @JsonDeserialize(using = PriceConverter.class)
    private Double daysHigh;
    @NonNull
    @JsonDeserialize(using = PriceConverter.class)
    private Double daysLow;
    @JsonDeserialize(using = MongoDateConverter.class)
    private Date dateTimeCreated;
    @JsonDeserialize(using = MongoDateConverter.class)
    private Date dateTimeUpdated;

    @PrePersist
    void createdAt() {
        this.dateTimeCreated = new Date();
    }

    @PreUpdate
    void updatedAt() {
        this.dateTimeUpdated = new Date();
    }
}


package uk.co.jamesmcguigan.forecaster.stock.historicalprice;

//import javax.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Data
@Document(collection = "price")
public class Price {

    @NonNull
    @JsonDeserialize(using = DateConverter.class)
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
    @NonNull
    @JsonDeserialize(using = DateConverter.class)
    private Date dateTimeCreated;

//    @PrePersist
//    void createdAt() {
//        this.dateTimeCreated = new Date();
//    }
//
//    @PreUpdate
//    void updatedAt() {
//        this.dateTimeUpdated = new Date();
//    }
}


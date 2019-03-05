package uk.co.jamesmcguigan.forecaster.stock.historicalprice;

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
    private Date date;
    @NonNull
    private double closing;
    @NonNull
    private double opening;
    @NonNull
    private double daysHigh;
    @NonNull
    private double daysLow;
    private Date dateTimeCreated;
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

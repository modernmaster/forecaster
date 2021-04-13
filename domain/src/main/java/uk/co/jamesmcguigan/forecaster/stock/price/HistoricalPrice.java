package uk.co.jamesmcguigan.forecaster.stock.price;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
//import org.springframework.data.mongodb.core.mapping.Document;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Data
//@Document(collection = "price")
public class HistoricalPrice {

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

}


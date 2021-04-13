package uk.co.jamesmcguigan.forecaster.stock.trend;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import uk.co.jamesmcguigan.forecaster.stock.price.DateConverter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Trend {
    private List<TrendPoint> series;
    @NonNull
    @JsonDeserialize(using = DateConverter.class)
    private Date dateTimeCreated;
}

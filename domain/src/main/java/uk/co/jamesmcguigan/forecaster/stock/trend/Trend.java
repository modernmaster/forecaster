package uk.co.jamesmcguigan.forecaster.stock.trend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Trend {
    private List<TrendPoint> series;
    @NonNull
    private Date dateTimeCreated;
}

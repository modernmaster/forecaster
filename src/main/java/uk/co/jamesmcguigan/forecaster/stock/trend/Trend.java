package uk.co.jamesmcguigan.forecaster.stock.trend;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Trend {
    private List<TrendPoint> series;
    private Date dateTimeCreated;
}

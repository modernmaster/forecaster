package uk.co.jamesmcguigan.forecaster.stock.trend;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.co.jamesmcguigan.forecaster.stock.historicalprice.DateConverter;
import uk.co.jamesmcguigan.forecaster.stock.historicalprice.PriceConverter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TrendPoint implements Comparable<TrendPoint> {
    @JsonDeserialize(using = DateConverter.class)
    private Date date;
    @JsonDeserialize(using = PriceConverter.class)
    private double pointValue;

    @Override
    public int compareTo(TrendPoint o) {
        return this.pointValue - o.pointValue > 0 ? 1 : -1;
    }
}

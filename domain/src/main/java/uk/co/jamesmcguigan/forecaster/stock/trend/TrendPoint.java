package uk.co.jamesmcguigan.forecaster.stock.trend;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.co.jamesmcguigan.forecaster.stock.price.PriceConverter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TrendPoint implements Comparable<TrendPoint> {
    private Date date;
    @JsonDeserialize(using = PriceConverter.class)
    private double pointValue;

    @Override
    public int compareTo(TrendPoint o) {
        return this.pointValue - o.pointValue > 0 ? 1 : -1;
    }
}

package uk.co.jamesmcguigan.forecaster.stock.trend;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TrendPoint implements Comparable<TrendPoint> {
    private Date date;
    private double pointValue;

    @Override
    public int compareTo(TrendPoint o) {
        return this.pointValue - o.pointValue > 0 ? 1 : -1;
    }
}

package uk.co.jamesmcguigan.forecaster.stock.trends;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TrendPoint {
    private Date date;
    private double pointValue;
}

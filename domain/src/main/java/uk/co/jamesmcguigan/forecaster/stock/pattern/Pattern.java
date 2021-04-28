package uk.co.jamesmcguigan.forecaster.stock.pattern;

import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Pattern {
    private PatternType patternType;
    private IntervalWindow window;
    private Double identifiedPrice;
    private Double targetPrice;
    @NonNull
    private LocalDateTime dateTimeCreated;
}

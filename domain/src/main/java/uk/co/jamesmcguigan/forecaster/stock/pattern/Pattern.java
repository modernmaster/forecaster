package uk.co.jamesmcguigan.forecaster.stock.pattern;

import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Pattern {
    private PatternType patternType;
    private Integer window;
    private Double identifiedPrice;
    private Double targetPrice;
    @NonNull
    private LocalDateTime dateTimeCreated;
}

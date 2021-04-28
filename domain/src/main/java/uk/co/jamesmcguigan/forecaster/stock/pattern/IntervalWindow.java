package uk.co.jamesmcguigan.forecaster.stock.pattern;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IntervalWindow {
    ONE_DAY(1),
    THREE_DAY(3),
    FIVE_DAY(5),
    TEN_DAY(10),
    TWENTY_DAY(20),
    ONE_HUNDRED_DAY(100),
    TWO_HUNDRED_DAY(200),
    TWO_HUNDRED_FIFTY_DAY(250);

    private final Integer value;

    public static IntervalWindow fromValue(Integer v) {
        for (IntervalWindow c : IntervalWindow.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(v));
    }
}

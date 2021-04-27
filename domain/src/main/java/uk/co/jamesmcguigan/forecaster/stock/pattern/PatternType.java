package uk.co.jamesmcguigan.forecaster.stock.pattern;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PatternType {

    BULLISH("BULLISH");

    private final String value;

    public static PatternType fromValue(String v) {
        for (PatternType c : PatternType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}

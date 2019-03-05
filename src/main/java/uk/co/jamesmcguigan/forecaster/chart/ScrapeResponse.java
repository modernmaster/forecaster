package uk.co.jamesmcguigan.forecaster.chart;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
class ScrapeResponse {
    private String uri;
    private String contents;
}

package uk.co.jamesmcguigan.forecaster.historicalprice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public
class Response {
    private String symbol;
    private String contents;
}

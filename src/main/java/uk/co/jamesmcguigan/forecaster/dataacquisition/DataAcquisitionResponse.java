package uk.co.jamesmcguigan.forecaster.dataacquisition;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public
class DataAcquisitionResponse {
    private String symbol;
    private String contents;
}

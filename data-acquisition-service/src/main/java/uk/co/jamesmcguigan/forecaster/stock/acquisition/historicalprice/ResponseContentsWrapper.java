package uk.co.jamesmcguigan.forecaster.stock.acquisition.historicalprice;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseContentsWrapper {

    private List<List<Double>> d;
}

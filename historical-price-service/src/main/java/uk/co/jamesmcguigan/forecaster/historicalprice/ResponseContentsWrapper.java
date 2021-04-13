package uk.co.jamesmcguigan.forecaster.historicalprice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseContentsWrapper {

    private List<List<Double>> d;
}

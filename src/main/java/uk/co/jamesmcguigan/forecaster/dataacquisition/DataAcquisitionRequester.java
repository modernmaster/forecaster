package uk.co.jamesmcguigan.forecaster.dataacquisition;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface DataAcquisitionRequester {
    Callable<DataAcquisitionResponse> create(String symbol);

    void saveResponses(Set<Future<DataAcquisitionResponse>> set);
}

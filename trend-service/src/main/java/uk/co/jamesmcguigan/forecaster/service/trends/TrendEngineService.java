package uk.co.jamesmcguigan.forecaster.service.trends;

import uk.co.jamesmcguigan.forecaster.stock.price.LivePriceEvent;

public interface TrendEngineService {
    void processTrendsForPriceEvent(LivePriceEvent livePriceEvent);
}

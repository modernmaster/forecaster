package uk.co.jamesmcguigan.forecaster.chart;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
interface ChartRepository extends MongoRepository<Chart, String> {
    Chart findBySymbol(String symbol);
    Chart findBySourceUri(String sourceUri);
}

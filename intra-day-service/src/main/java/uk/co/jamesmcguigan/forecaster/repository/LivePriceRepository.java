package uk.co.jamesmcguigan.forecaster.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uk.co.jamesmcguigan.forecaster.stock.price.LivePrice;

@RepositoryRestResource
public interface LivePriceRepository extends CrudRepository<LivePrice, Long> {
}

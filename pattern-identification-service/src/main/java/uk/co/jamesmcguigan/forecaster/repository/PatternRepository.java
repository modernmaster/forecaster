package uk.co.jamesmcguigan.forecaster.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uk.co.jamesmcguigan.forecaster.service.pattern.PatternTransaction;

@RepositoryRestResource
public interface PatternRepository extends PagingAndSortingRepository<PatternTransaction, Long> {
}

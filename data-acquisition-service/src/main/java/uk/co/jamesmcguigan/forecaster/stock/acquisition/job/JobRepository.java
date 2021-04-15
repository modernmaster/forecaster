package uk.co.jamesmcguigan.forecaster.service.stock.acquisition.job;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<Job, String> {
}

package uk.co.jamesmcguigan.forecaster.stock.acquisition.job;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<Job, String> {
}

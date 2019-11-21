package uk.co.jamesmcguigan.forecaster.stock.acquisition.job;

import org.springframework.data.mongodb.repository.MongoRepository;
import uk.co.jamesmcguigan.forecaster.stock.acquisition.job.Job;

public interface JobRepository extends MongoRepository<Job, String> {
}

package uk.co.jamesmcguigan.forecaster.service.stock.acquisition.job;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uk.co.jamesmcguigan.forecaster.service.stock.acquisition.historicalprice.CreateHistoricalPriceEvent;

@Service
@AllArgsConstructor
public class JobService {

    private CreateHistoricalPriceEvent createHistoricalPriceEvent;
    private JobRepository jobRepository;

    public String createJob(String entityId, String className) {
        LocalDateTime date = LocalDateTime.now();
        Job job = Job.builder()
                .created(date)
                .entityId(entityId)
                .entityClassifer(className)
                .status(Status.PENDING)
                .updated(date)
                .build();
        Job persistedJob = jobRepository.save(job);
        //TODO strategy pattern to determine which event to create
        createHistoricalPriceEvent.send(new JobQueueItem(persistedJob.getId()));
        return persistedJob.getId();
    }

    public Job getJob(String id) {
        return jobRepository.findById(id).orElse(null);
    }

    public void save(Job job) {
        jobRepository.save(job);
    }
}

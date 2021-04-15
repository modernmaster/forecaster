package uk.co.jamesmcguigan.forecaster.service.stock.acquisition.job;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import uk.co.jamesmcguigan.forecaster.service.stock.acquisition.historicalprice.CreateHistoricalPriceEvent;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JobServiceTest {

    private JobService jobService;
    @Mock
    private JobRepository jobRepository;
    @Mock
    private CreateHistoricalPriceEvent createHistoricalPriceEvent;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        jobService = new JobService(createHistoricalPriceEvent, jobRepository);
    }

    @Test
    public void testCreateJobWillAddEntryAndPlaceOnQueue() {
        String id = "1";
        String jobId = "100";
        String entityClassifer = "classifer";
        Job job = Job.builder()
                .id(jobId)
                .build();
        when(jobRepository.save(any(Job.class))).thenReturn(job);

        String returnedJobId = jobService.createJob(id, entityClassifer);

        assertThat(jobId, equalTo(returnedJobId));
        verify(createHistoricalPriceEvent).send(any(JobQueueItem.class));
        verify(jobRepository).save(any(Job.class));
    }

    @Test
    public void testGetJobWillReturnJobEntityAnd200() {
        String jobId = "100";
        Job job = Job.builder()
                .id(jobId)
                .build();
        when(jobRepository.findById(jobId)).thenReturn(java.util.Optional.of(job));

        Job returnedJob = jobService.getJob(jobId);

        assertThat(returnedJob, equalTo(job));
    }

    @Test
    public void testSaveJob(){
        String jobId = "100";
        Job job = Job.builder()
                .id(jobId)
                .build();
        jobService.save(job);
        verify(jobRepository).save(job);
    }
}

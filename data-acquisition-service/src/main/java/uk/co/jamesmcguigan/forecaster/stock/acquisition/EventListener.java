package uk.co.jamesmcguigan.forecaster.service.stock.acquisition;

import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import uk.co.jamesmcguigan.forecaster.service.stock.acquisition.historicalprice.HistoricalPriceService;
import uk.co.jamesmcguigan.forecaster.service.stock.acquisition.job.Job;
import uk.co.jamesmcguigan.forecaster.service.stock.acquisition.job.JobQueueItem;
import uk.co.jamesmcguigan.forecaster.service.stock.acquisition.job.JobService;

@AllArgsConstructor
public class EventListener {

    private static final String RECEIVED_TOPIC_1_MESSAGE = "Received topic 1  message: ";

    private HistoricalPriceService historicalPriceService;
    private JobService jobService;

    @RabbitListener(queues = {EventConfiguration.QUEUE_PRICE_HISTORICAL})
    public void receiveMessageFromHistoricalPriceTopic(JobQueueItem jobQueueItem) {
        DataAcquisitionServiceApplication.logger.info(RECEIVED_TOPIC_1_MESSAGE + jobQueueItem.getId());
        Job job = Preconditions.checkNotNull(jobService.getJob(jobQueueItem.getId()));
        historicalPriceService.processRequest(job);
    }

    @RabbitListener(queues = {EventConfiguration.PRICE_CURRENT})
    public void receiveMessageFromTopic2(JobQueueItem jobQueueItem) {
        DataAcquisitionServiceApplication.logger.info(RECEIVED_TOPIC_1_MESSAGE + jobQueueItem.getId());
        Job job = Preconditions.checkNotNull(jobService.getJob(jobQueueItem.getId()));
        historicalPriceService.processRequest(job);
    }

}

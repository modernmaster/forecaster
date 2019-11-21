package uk.co.jamesmcguigan.forecaster.stock.acquisition.controller;

import javax.validation.constraints.NotNull;
import java.net.URI;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.co.jamesmcguigan.forecaster.stock.acquisition.job.Job;
import uk.co.jamesmcguigan.forecaster.stock.acquisition.job.JobService;
import uk.co.jamesmcguigan.forecaster.stock.acquisition.job.Status;

@RestController
@RequiredArgsConstructor
@RequestMapping("/data-acquisition/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @NotNull
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getJob(@PathVariable String id) {
        Preconditions.checkNotNull(id);
        Job job = checkResponseIsNull(jobService.getJob(id));
        if (job.getStatus().equals(Status.COMPLETED)) {
            String path = String.format("/%s/%s", job.getEntityClassifer(), job.getEntityId());
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .replacePath(path)
                    .build()
                    .toUri();
            return ResponseEntity.status(HttpStatus.SEE_OTHER)
                    .location(location)
                    .body(job);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(job);
    }

    private Job checkResponseIsNull(Job job) {
        if (job == null) {
            throw new EntityException.JobNotFoundException();
        }
        return job;
    }
}

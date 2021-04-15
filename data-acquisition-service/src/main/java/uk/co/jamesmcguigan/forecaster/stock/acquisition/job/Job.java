package uk.co.jamesmcguigan.forecaster.service.stock.acquisition.job;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Data
@Getter
@Builder
public class Job implements Serializable {
    @Id
    private String id;
    private String entityId;
    private String entityClassifer;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Status status;
}

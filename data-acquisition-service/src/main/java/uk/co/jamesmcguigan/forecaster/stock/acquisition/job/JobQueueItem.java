package uk.co.jamesmcguigan.forecaster.service.stock.acquisition.job;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JobQueueItem implements Serializable {
    @JsonProperty
    private String Id;
}

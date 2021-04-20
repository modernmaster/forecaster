package uk.co.jamesmcguigan.forecaster.service.stock.acquisition.historicalprice;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
public class PriceRepresentation {

    @NonNull
    @JsonProperty
    private Date date;

    @NonNull
    @JsonProperty
    private Double closing;

    @JsonProperty
    @NonNull
    private Double opening;

    @JsonProperty
    @NonNull
    private Double daysHigh;

    @JsonProperty
    @NonNull
    private Double daysLow;

    @NonNull
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date dateTimeCreated;

}


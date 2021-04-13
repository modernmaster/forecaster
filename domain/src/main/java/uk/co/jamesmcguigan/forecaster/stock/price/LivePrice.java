package uk.co.jamesmcguigan.forecaster.stock.price;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import uk.co.jamesmcguigan.forecaster.stock.Audit;
import uk.co.jamesmcguigan.forecaster.stock.DoubleConvertor;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Entity
public class LivePrice extends Audit {
    @NonNull
    private String symbol;
    @JsonDeserialize(using = DoubleConvertor.class)
    private String price;
    @NonNull
    private String volume;
}

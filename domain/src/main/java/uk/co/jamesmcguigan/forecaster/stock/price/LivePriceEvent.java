package uk.co.jamesmcguigan.forecaster.stock.price;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import uk.co.jamesmcguigan.forecaster.stock.Audit;
import uk.co.jamesmcguigan.forecaster.stock.DoubleConvertor;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class LivePriceEvent extends Audit {
    @NonNull
    private String symbol;
    @JsonDeserialize(using = DoubleConvertor.class)
    private Double price;
    @NonNull
    private Integer volume;
}

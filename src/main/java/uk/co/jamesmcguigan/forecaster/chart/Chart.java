package uk.co.jamesmcguigan.forecaster.chart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Document(collection = "chart")
public class Chart {

    @Id
 //   @Indexed(unique = true)
    private String symbol;
    private String sourceUri;
    private String body;
}

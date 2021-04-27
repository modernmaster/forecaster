package uk.co.jamesmcguigan.forecaster.service.pattern;

import lombok.*;
import uk.co.jamesmcguigan.forecaster.stock.pattern.PatternType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PatternTransaction {
    @Id @GeneratedValue
    private Long id;
    private String symbol;
    @Enumerated(EnumType.STRING)
    private PatternType pattern;
    @OneToMany(targetEntity = Notification.class, fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @Column(name = "notification")
    private List<Notification> notification;
    private LocalDateTime createdDateTime;
    private Double targetPrice;
    private Double identifiedPrice;

    public Optional<Double> getTargetPrice() {
        return Optional.ofNullable(targetPrice);
    }
}

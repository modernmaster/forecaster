package uk.co.jamesmcguigan.forecaster.service.pattern;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@Builder
@EqualsAndHashCode
public class Notification {
    @Id @GeneratedValue
    private Long id;
}

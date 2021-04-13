package uk.co.jamesmcguigan.forecaster.stock;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public abstract class Audit {

    @Column(name = "id", nullable = false, updatable = false)
    private @GeneratedValue
    @Id
    Long id;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private Long createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private Long modifiedDate;

    @Column(name = "created_by", nullable = false, updatable = false)
    @CreatedBy
    private String createdBy;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;

    @Column(name = "version", nullable = false, updatable = false)
    @Version
    private Long version;
}

package pl.nowak.producter.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "product")
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @Column(unique = true)
    private String sku;

    private String name;

    private Boolean deleted;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime created;
}

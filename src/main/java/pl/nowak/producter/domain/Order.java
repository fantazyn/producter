package pl.nowak.producter.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @SequenceGenerator(name = "seq_order", sequenceName = "seq_order", allocationSize = 1)
    @GeneratedValue(generator = "seq_order")
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(name = "buyer_email")
    private String buyerEmail;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime created;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<ProductInOrder> products = new ArrayList();
}

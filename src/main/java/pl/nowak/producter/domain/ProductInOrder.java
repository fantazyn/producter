package pl.nowak.producter.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "product_in_order")
public class ProductInOrder {

//    @EmbeddedId
//    private ProductInOrderId productInOrderId;

    @Id
    @SequenceGenerator(name = "seq_product_in_order", sequenceName = "seq_product_in_order", allocationSize = 1)
    @GeneratedValue(generator = "seq_product_in_order")
    @Column(updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "number")
    private Long number;
}

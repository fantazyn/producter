package pl.nowak.producter.domain;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class ProductInOrderId implements Serializable {

    private Product product;

    private Order order;
}

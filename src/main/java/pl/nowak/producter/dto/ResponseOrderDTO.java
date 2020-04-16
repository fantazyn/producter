package pl.nowak.producter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class ResponseOrderDTO {

    private String buyerEmail;

    private BigDecimal totalPrice;

    private LocalDateTime created;

    private Map<ProductDTO, Long> products = new HashMap<>();
}
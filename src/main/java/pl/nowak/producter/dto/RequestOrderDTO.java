package pl.nowak.producter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import java.util.Map;

@Data
@NoArgsConstructor
public class RequestOrderDTO {

    @Email
    private String buyerEmail;

    private Map<String, Long> products; // SKU of product and number
}
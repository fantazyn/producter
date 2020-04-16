package pl.nowak.producter.controller.order;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.nowak.producter.domain.Order;
import pl.nowak.producter.dto.RequestOrderDTO;
import pl.nowak.producter.dto.ResponseOrderDTO;
import pl.nowak.producter.services.order.OrderService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @ApiOperation(value = "Retrieves all orders with products for given period of time")
    public ResponseEntity<List<ResponseOrderDTO>> getAllOrderForPeriod(
            @RequestParam(value = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(value = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Order> orders = orderService.getAllForPeriod(startDate, endDate);
        return ResponseEntity.status(HttpStatus.OK).body(orders.stream()
                .map(orderService::convert)
                .collect(Collectors.toList()));
    }

    @PostMapping
    @ApiOperation(value = "Creates an order with products")
    public ResponseEntity<ResponseOrderDTO> createOrder(@Valid @RequestBody RequestOrderDTO requestOrderDTO) {
        Order order = orderService.create(requestOrderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.convert(order));
    }
}

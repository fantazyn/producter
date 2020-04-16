package pl.nowak.producter.services.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.nowak.producter.domain.Order;
import pl.nowak.producter.domain.ProductInOrder;
import pl.nowak.producter.dto.ProductDTO;
import pl.nowak.producter.dto.RequestOrderDTO;
import pl.nowak.producter.dto.ResponseOrderDTO;
import pl.nowak.producter.repositories.order.OrderRepository;
import pl.nowak.producter.repositories.product.ProductRepository;
import pl.nowak.producter.services.product.ProductService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Transactional(readOnly = true)
    public List<Order> getAllForPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findAllForPeriod(startDate, endDate);
    }

    @Transactional
    public Order create(RequestOrderDTO requestOrderDTO) {
        log.info("Creating order for {}", requestOrderDTO.getBuyerEmail());
        Order order = new Order();
        order.setBuyerEmail(requestOrderDTO.getBuyerEmail());
        if (requestOrderDTO.getProducts() != null) {
            for (Map.Entry<String, Long> entry : requestOrderDTO.getProducts().entrySet()) {
                ProductInOrder productInOrder = new ProductInOrder();
                productInOrder.setNumber(entry.getValue());
                productInOrder.setOrder(order);
                productInOrder.setProduct(productRepository.findById(entry.getKey())
                        .orElseThrow(() -> new RuntimeException(String.format("There is no product with SKU %s", entry.getKey()))));
                order.getProducts().add(productInOrder);
            }
        }
        return orderRepository.save(order);
    }

    public ResponseOrderDTO convert(Order order) {
        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();
        responseOrderDTO.setBuyerEmail(order.getBuyerEmail());
        responseOrderDTO.setCreated(order.getCreated());
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (ProductInOrder productInOrder : order.getProducts()) {
            Long number = productInOrder.getNumber();
            totalPrice = productInOrder.getProduct().getPrice().multiply(BigDecimal.valueOf(number)).add(totalPrice);
            ProductDTO productDTO = productService.convert(productInOrder.getProduct());
            responseOrderDTO.getProducts().put(productDTO, number);
        }
        responseOrderDTO.setTotalPrice(totalPrice);
        return responseOrderDTO;
    }
}

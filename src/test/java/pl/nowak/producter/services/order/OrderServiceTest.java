package pl.nowak.producter.services.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Service;
import pl.nowak.producter.domain.Order;
import pl.nowak.producter.domain.Product;
import pl.nowak.producter.domain.ProductInOrder;
import pl.nowak.producter.dto.ProductDTO;
import pl.nowak.producter.dto.RequestOrderDTO;
import pl.nowak.producter.dto.ResponseOrderDTO;
import pl.nowak.producter.repositories.order.OrderRepository;
import pl.nowak.producter.repositories.product.ProductRepository;
import pl.nowak.producter.services.product.ProductService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.easymock.EasyMock.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderServiceTest extends EasyMockSupport {

    private OrderService orderService;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private ProductService productService;

    @Before
    public void setup() {
        productRepository = createMock(ProductRepository.class);
        orderRepository = createMock(OrderRepository.class);
        productService = createMock(ProductService.class);
        orderService = new OrderService(productRepository, orderRepository, productService);
    }

    @Test
    public void shouldGetAllOrdersForPeriods() {
        //given
        expect(orderRepository.findAllForPeriod(anyObject(LocalDateTime.class), anyObject(LocalDateTime.class))).andReturn(new ArrayList<>());
        replay(orderRepository);

        //when
        List<Order> orders = orderService.getAllForPeriod(LocalDateTime.now(), LocalDateTime.now());

        //then
        verify(orderRepository);
        assertThat(orders != null);
    }

    @Test
    public void shouldCreateOrder() {
        //given
        expect(productRepository.findById(anyString())).andReturn(Optional.of(new Product()));
        expect(orderRepository.save(anyObject(Order.class))).andReturn(new Order());
        replay(orderRepository, productRepository);

        //when
        Order order = orderService.create(generateRequestOrderDTO());

        //then
        verify(orderRepository, productRepository);
        assertThat(order != null);
    }

    @Test
    public void shouldConvertOrder() {
        //given
        expect(productService.convert(anyObject())).andReturn(new ProductDTO()).times(2);
        replay(productService);

        //when
        ResponseOrderDTO responseOrderDTO = orderService.convert(generateOrder());

        //then
        verify(productService);
        assertThat(responseOrderDTO != null);
        assertThat(Long.valueOf(72l).equals(responseOrderDTO.getTotalPrice().longValueExact()));
        assertThat("email".equals(responseOrderDTO.getBuyerEmail()));
    }

    private RequestOrderDTO generateRequestOrderDTO() {
        RequestOrderDTO requestOrderDTO = new RequestOrderDTO();
        requestOrderDTO.setBuyerEmail("some Email");
        Map<String, Long> products = new HashMap<>();
        products.put("sku", 7l);
        requestOrderDTO.setProducts(products);
        return requestOrderDTO;
    }

    private Order generateOrder() {
        Order order = new Order();
        order.setBuyerEmail("email");
        order.getProducts().add(generateProductInOrder(BigDecimal.ONE, 2l));
        order.getProducts().add(generateProductInOrder(BigDecimal.TEN, 7l));
        return order;
    }

    private Product generateProduct(BigDecimal price) {
        Product product = new Product();
        product.setName("name");
        product.setPrice(price);
        product.setSku("sku");
        return product;
    }

    private ProductInOrder generateProductInOrder(BigDecimal price, Long number) {
        ProductInOrder productInOrder = new ProductInOrder();
        productInOrder.setProduct(generateProduct(price));
        productInOrder.setNumber(number);
        return productInOrder;
    }
}

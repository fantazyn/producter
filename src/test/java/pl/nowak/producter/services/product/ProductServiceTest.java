package pl.nowak.producter.services.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Service;
import pl.nowak.producter.domain.Product;
import pl.nowak.producter.dto.ProductDTO;
import pl.nowak.producter.repositories.product.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.easymock.EasyMock.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductServiceTest extends EasyMockSupport {

    private ProductRepository productRepository;
    private ProductService productService;

    @Before
    public void setup() {
        productRepository = createMock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    public void shouldGetAllProducts() {
        //given
        expect(productRepository.findAll()).andReturn(new ArrayList<>());
        replay(productRepository);

        //when
        List<Product> products = productService.getAll();

        //then
        verify(productRepository);
        assertThat(products != null);
    }

    @Test
    public void shouldCreateProduct() {
        //given
        expect(productRepository.save(anyObject())).andReturn(generateProduct(BigDecimal.ZERO));
        replay(productRepository);

        //when
        Product product = productService.create(new ProductDTO());

        //then
        verify(productRepository);
        assertThat(product != null);
    }

    @Test
    public void shouldUpdateProduct() {
        //given
        expect(productRepository.save(anyObject())).andReturn(generateProduct(BigDecimal.ZERO));
        expect(productRepository.findById(anyString())).andReturn(Optional.of(new Product()));
        replay(productRepository);

        //when
        Product product = productService.update("sku", new ProductDTO());

        //then
        verify(productRepository);
        assertThat(product != null);
    }

    @Test
    public void shouldConvertProduct() {
        //given

        //when
        ProductDTO productDTO = productService.convert(generateProduct(BigDecimal.valueOf(72l)));

        //then
        assertThat(productDTO != null);
        assertThat(Long.valueOf(72l).equals(productDTO.getPrice().longValueExact()));
        assertThat("name".equals(productDTO.getName()));
        assertThat("sku".equals(productDTO.getSku()));
        assertThat(LocalDate.of(2019, 7, 24).equals(productDTO.getCreated()));
    }

    @Test
    public void shouldDeleteProduct() {
        //given
        productRepository.deleteBySku(anyString());
        expectLastCall();
        replay(productRepository);

        //when
        productService.deleteBySku("sku");

        //then
        verify(productRepository);
    }

    private Product generateProduct(BigDecimal price) {
        Product product = new Product();
        product.setName("name");
        product.setPrice(price);
        product.setCreated(LocalDateTime.of(2019, 7, 24, 12, 16, 0));
        product.setSku("sku");
        return product;
    }
}

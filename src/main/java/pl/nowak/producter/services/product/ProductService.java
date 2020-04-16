package pl.nowak.producter.services.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import pl.nowak.producter.domain.Product;
import pl.nowak.producter.dto.ProductDTO;
import pl.nowak.producter.repositories.product.ProductRepository;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> getAll() {
        //we should probably use Pageable if it's used for UI
        //should retrieve only not deleted products?
        return productRepository.findAll();
    }

    @Transactional
    public Product update(String sku, ProductDTO productDTO) {
        if (StringUtils.isEmpty(sku)) {
            log.warn("SKU is required to change Product");
            return null; //throw some exception and return proper error message in response
        }
        Optional<Product> optionalProduct = productRepository.findById(sku);
        if (optionalProduct.isEmpty()) {
            log.error("No Product found with SKU: {}", productDTO.getSku());
            return null; //throw some exception and return proper error message in response
        } else {
            Product product = convert(optionalProduct.get(), productDTO);
            productRepository.save(product);
            return product;
        }
    }

    @Transactional
    public void deleteBySku(String sku) {
        productRepository.deleteBySku(sku);
    }

    @Transactional
    public Product create(ProductDTO productDTO) {
        Product product = convert(new Product(), productDTO);
        return productRepository.save(product);
    }

    public ProductDTO convert(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCreated(product.getCreated().toLocalDate());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setSku(product.getSku());
        return productDTO;
    }

    private Product convert(Product product, ProductDTO productDTO) {
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setSku(productDTO.getSku());
        return product;
    }
}

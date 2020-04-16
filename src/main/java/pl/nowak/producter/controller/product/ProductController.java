package pl.nowak.producter.controller.product;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.nowak.producter.domain.Product;
import pl.nowak.producter.dto.ProductDTO;
import pl.nowak.producter.services.product.ProductService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @ApiOperation(value = "Retrieves all products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(products.stream()
                .map(productService::convert)
                .collect(Collectors.toList()));
    }

    @PostMapping
    @ApiOperation(value = "Creates a product")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product product = productService.create(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.convert(product));
    }

    @PutMapping("/{sku}")
    @ApiOperation(value = "Updates a Product")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable String sku, @Valid @RequestBody ProductDTO productDTO) {
        Product product = productService.update(sku, productDTO);
        if (product == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(productService.convert(product));
    }

    @DeleteMapping("/{sku}")
    @ApiOperation(value = "Archives a product")
    public ResponseEntity deleteProduct(@PathVariable String sku) {
        productService.deleteBySku(sku);
        return ResponseEntity.notFound().build();
    }
}

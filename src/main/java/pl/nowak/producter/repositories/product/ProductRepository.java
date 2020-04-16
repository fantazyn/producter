package pl.nowak.producter.repositories.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.nowak.producter.domain.Product;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface ProductRepository extends JpaRepository<Product, String> {

    @Modifying
    @Query("UPDATE Product p SET p.deleted = true WHERE p.sku = :sku")
    void deleteBySku(@Param("sku") String sku);
}

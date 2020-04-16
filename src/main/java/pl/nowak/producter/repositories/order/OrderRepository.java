package pl.nowak.producter.repositories.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.nowak.producter.domain.Order;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.created BETWEEN :beginDate AND :endDate")
    List<Order> findAllForPeriod(@Param("beginDate") LocalDateTime beginDate, @Param("endDate") LocalDateTime endDate);
}

package nl.miw.se.cohort7.eindproject.rise.billy.repository;

import nl.miw.se.cohort7.eindproject.rise.billy.model.BarOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BarOrderRepository extends JpaRepository<BarOrder, Long> {
    List<BarOrder> findAllByCustomerId(Long id);
}

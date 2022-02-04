package nl.miw.se.cohort7.eindproject.rise.billy.repository;

import nl.miw.se.cohort7.eindproject.rise.billy.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

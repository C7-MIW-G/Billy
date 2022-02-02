package nl.miw.se.cohort7.eindproject.rise.billy.service;

import nl.miw.se.cohort7.eindproject.rise.billy.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {
    Optional<Product> findByProductName(String title);

    Optional<Product> findByProductId(Long id);

    List<Product> findAll();

    void save(Product product);

    void delete(Product product);
}

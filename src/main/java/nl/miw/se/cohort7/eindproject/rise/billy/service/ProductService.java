package nl.miw.se.cohort7.eindproject.rise.billy.service;

import nl.miw.se.cohort7.eindproject.rise.billy.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<Product> findByProductId(Long id);

    List<Product> findAll();

    //BarOrder currently still uses this!
}

package nl.miw.se.cohort7.eindproject.rise.billy.service;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.ProductDto;
import nl.miw.se.cohort7.eindproject.rise.billy.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<ProductDto> findByProductId(Long id);

    List<ProductDto> findAll();

    //BarOrder currently still uses this!
}

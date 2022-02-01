package nl.miw.se.cohort7.eindproject.rise.billy.service.implementation;

import nl.miw.se.cohort7.eindproject.rise.billy.model.Product;
import nl.miw.se.cohort7.eindproject.rise.billy.repository.ProductRepository;
import nl.miw.se.cohort7.eindproject.rise.billy.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Martijn Gäbler <m.gabler@st.hanze.nl>
 * Date created: 01/02/2022
 * Dit is wat het programma doet.
 */

@Service
public class ProductServiceImplementation implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImplementation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findByProductName(String productName) {
        return productRepository.findByProductName(productName);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }
}

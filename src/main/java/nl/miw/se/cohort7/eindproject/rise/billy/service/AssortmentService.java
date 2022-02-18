package nl.miw.se.cohort7.eindproject.rise.billy.service;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.CategoryDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.ProductDto;

import java.util.List;
import java.util.Optional;


public interface AssortmentService {

    List<CategoryDto> findAllCategories();

    void saveCategory(CategoryDto categoryDto);

    Optional<CategoryDto> findCategoryById(Long id);

    List<ProductDto> findAllProductOfCategory(Long id);

    Optional<ProductDto> findByProductId(Long id);

    List<ProductDto> findAllProducts();

    void saveProduct(ProductDto productDto);

    void deleteProduct(ProductDto productDto);
}

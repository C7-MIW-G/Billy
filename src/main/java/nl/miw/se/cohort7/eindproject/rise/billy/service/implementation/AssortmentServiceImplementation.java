package nl.miw.se.cohort7.eindproject.rise.billy.service.implementation;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.CategoryDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.ProductDto;
import nl.miw.se.cohort7.eindproject.rise.billy.model.Category;
import nl.miw.se.cohort7.eindproject.rise.billy.model.Product;
import nl.miw.se.cohort7.eindproject.rise.billy.repository.CategoryRepository;
import nl.miw.se.cohort7.eindproject.rise.billy.repository.ProductRepository;
import nl.miw.se.cohort7.eindproject.rise.billy.service.AssortmentService;
import nl.miw.se.cohort7.eindproject.rise.billy.service.DtoConverter.AssortmentConverters;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * The service layer that connects the front- and back-end of the category and product objects
 */

@Service
public class AssortmentServiceImplementation implements AssortmentService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private AssortmentConverters assortmentConverters;

    public AssortmentServiceImplementation(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.assortmentConverters = new AssortmentConverters();
    }

    //Category-related
    private void getAndSetCategoryProductList(Category category){
        Optional<Category> optionalCategory = categoryRepository.findById(category.getCategoryId());
        optionalCategory.ifPresent(value -> category.setProducts(value.getProducts()));
    }


    @Override
    public List<CategoryDto> findAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(assortmentConverters::convertCategoryToDto)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public void saveCategory(CategoryDto categoryDto) {
        Category category = assortmentConverters.convertDtoToCategory(categoryDto);
        getAndSetCategoryProductList(category);
        categoryRepository.save(category);
    }

    @Override
    public Optional<CategoryDto> findCategoryById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.map(assortmentConverters::convertCategoryToDto);
    }

    @Override
    public List<CategoryDto> findCategoryByName(String name) {
        return categoryRepository.findByCategoryName(name)
                .stream()
                .map(assortmentConverters::convertCategoryToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findAllProductOfCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        List<ProductDto> productList = new ArrayList<>();
        optionalCategory.ifPresent(
                category -> productList.addAll(category.getProducts()
                        .stream()
                        .map(assortmentConverters::convertProductToDto)
                        .sorted()
                        .collect(Collectors.toList())));
        return productList;
    }

    @Override
    public void deleteCategory(CategoryDto categoryDto) {
        Category category = assortmentConverters.convertDtoToCategory(categoryDto);
        getAndSetCategoryProductList(category);
        if(category.getProducts().isEmpty()){
            categoryRepository.delete(category);
        }
    }


    //Product-related
    private Optional<Category> getCategoryOfProductDto(ProductDto productDto){
        return categoryRepository.findById(productDto.getCategoryDto().getCategoryId());
    }

    @Override
    public Optional<ProductDto> findByProductId(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.map(assortmentConverters::convertProductToDto);
    }

    @Override
    public List<ProductDto> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(assortmentConverters::convertProductToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveProduct(ProductDto productDto) {
        if(productDto.getCategoryDto() != null){
            productRepository.save(assortmentConverters.convertDtoToProduct(productDto, getCategoryOfProductDto(productDto)));
        }
    }

    @Override
    public void deleteProduct(ProductDto productDto) {
        productRepository.delete(assortmentConverters.convertDtoToProduct(productDto, getCategoryOfProductDto(productDto)));
    }
}
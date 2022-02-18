package nl.miw.se.cohort7.eindproject.rise.billy.service.implementation;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.CategoryDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.ProductDto;
import nl.miw.se.cohort7.eindproject.rise.billy.model.Category;
import nl.miw.se.cohort7.eindproject.rise.billy.model.Product;
import nl.miw.se.cohort7.eindproject.rise.billy.repository.CategoryRepository;
import nl.miw.se.cohort7.eindproject.rise.billy.repository.ProductRepository;
import nl.miw.se.cohort7.eindproject.rise.billy.service.AssortmentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public AssortmentServiceImplementation(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    //Category-related
    private CategoryDto convertCategoryToDto(Category category){
        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setCategoryId(category.getCategoryId());
        categoryDto.setCategoryName(category.getCategoryName());

        return categoryDto;
    }

    private Category convertDtoToCategory(CategoryDto categoryDto){
        Category category = new Category();
        category.setCategoryId(categoryDto.getCategoryId());

        Optional<Category> optionalCategory = categoryRepository.findById(category.getCategoryId());
        if (optionalCategory.isPresent()){
            category = optionalCategory.get();
        }

        category.setCategoryName(categoryDto.getCategoryName());

        return category;
    }


    @Override
    public List<CategoryDto> findAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::convertCategoryToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveCategory(CategoryDto categoryDto) {
        categoryRepository.save(convertDtoToCategory(categoryDto));
    }

    @Override
    public Optional<CategoryDto> findCategoryById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.map(this::convertCategoryToDto);
    }

    @Override
    public List<ProductDto> findAllProductOfCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        List<ProductDto> productList = new ArrayList<>();
        optionalCategory.ifPresent(
                category -> productList.addAll(category.getProducts()
                        .stream()
                        .map(this::convertProductToDto)
                        .collect(Collectors.toList())));
        return productList;
    }

    @Override
    public void deleteCategory(CategoryDto categoryDto) {
        Category category = convertDtoToCategory(categoryDto);
        if(category.getProducts().isEmpty()){
            categoryRepository.delete(category);
        }
    }


    //Product-related
    private ProductDto convertProductToDto(Product product){
        ProductDto productDto = new ProductDto();

        productDto.setProductId(product.getProductId());
        productDto.setProductName(product.getProductName());
        productDto.setProductPrice(product.getProductPrice());

        productDto.setCategoryDto(convertCategoryToDto(product.getCategory()));

        return productDto;
    }

    private Product convertDtoToProduct(ProductDto productDto){
        Product product = new Product();

        product.setProductId(productDto.getProductId());
        product.setProductName(productDto.getProductName());
        product.setProductPrice(productDto.getProductPrice());

        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryDto().getCategoryId());
        optionalCategory.ifPresent(product::setCategory);
        return product;
    }


    @Override
    public Optional<ProductDto> findByProductId(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.map(this::convertProductToDto);
    }

    @Override
    public List<ProductDto> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertProductToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveProduct(ProductDto productDto) {
        if(productDto.getCategoryDto() != null){
            productRepository.save(convertDtoToProduct(productDto));
        }
    }

    @Override
    public void deleteProduct(ProductDto productDto) {
        productRepository.delete(convertDtoToProduct(productDto));

    }
}

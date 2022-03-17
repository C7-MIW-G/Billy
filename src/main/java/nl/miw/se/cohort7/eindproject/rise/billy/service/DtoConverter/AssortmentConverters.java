package nl.miw.se.cohort7.eindproject.rise.billy.service.DtoConverter;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.CategoryDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.ProductDto;
import nl.miw.se.cohort7.eindproject.rise.billy.model.Category;
import nl.miw.se.cohort7.eindproject.rise.billy.model.Product;

import java.util.Optional;

/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * Converters of Category and Product From and To DTO's.
 */
public class AssortmentConverters {


    public CategoryDto convertCategoryToDto(Category category){
        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setCategoryId(category.getCategoryId());
        categoryDto.setCategoryName(category.getCategoryName());

        return categoryDto;
    }

    public Category convertDtoToCategory(CategoryDto categoryDto){
        Category category = new Category();

        category.setCategoryId(categoryDto.getCategoryId());
        category.setCategoryName(categoryDto.getCategoryName());

        return category;
    }

    public ProductDto convertProductToDto(Product product){
        ProductDto productDto = new ProductDto();

        productDto.setProductId(product.getProductId());
        productDto.setProductName(product.getProductName());
        productDto.setProductPrice(product.getProductPrice());
        productDto.setProductOfAge(product.isProductOfAge());

        productDto.setCategoryDto(convertCategoryToDto(product.getCategory()));

        return productDto;
    }

    public Product convertDtoToProduct(ProductDto productDto, Optional<Category> optionalCategory){
        Product product = new Product();

        product.setProductId(productDto.getProductId());
        product.setProductName(productDto.getProductName());
        product.setProductPrice(productDto.getProductPrice());
        product.setProductOfAge(productDto.isProductOfAge());

        optionalCategory.ifPresent(product::setCategory);
        return product;
    }
}

package nl.miw.se.cohort7.eindproject.rise.billy.testing.unittesting;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.CategoryDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.ProductDto;
import nl.miw.se.cohort7.eindproject.rise.billy.model.Category;
import nl.miw.se.cohort7.eindproject.rise.billy.model.Product;
import nl.miw.se.cohort7.eindproject.rise.billy.service.DtoConverter.AssortmentConverters;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * Test Converters of Category and Product From and To DTO's.
 */
public class AssortmentConvertersTest {
    private static final Long DEFAULT_ID = 1L;

    private AssortmentConverters assortmentConverters = new AssortmentConverters();

    private Category createCategory(Long id, String name){
        Category category = new Category();
        category.setCategoryId(id);
        category.setCategoryName(name);
        return category;
    }

    private CategoryDto createCategoryDto(Long id, String name){
        CategoryDto dto = new CategoryDto();
        dto.setCategoryId(id);
        dto.setCategoryName(name);
        return dto;
    }

    private Product createProduct(Long id, String name, double price, Category category, boolean restriction){
        Product product = new Product();
        product.setProductId(id);
        product.setProductName(name);
        product.setProductPrice(price);
        product.setCategory(category);
        product.setProductOfAge(restriction);
        return product;
    }

    private Category makeDefaultCategory(){
        Category category = createCategory(DEFAULT_ID, "Beer");
        List<Product> productList = new ArrayList<>();
        productList.add(
                createProduct(DEFAULT_ID, "beer1", 2.0, category, false));
        productList.add(
                createProduct(2L, "beer2", 2.1, category, true));
        category.setProducts(productList);
        return category;
    }

    @Test
    void convertCategoryToDto(){
        Category category = makeDefaultCategory();

        CategoryDto dto = assortmentConverters.convertCategoryToDto(category);

        assertEquals(dto.getCategoryId(), category.getCategoryId());
        assertEquals(dto.getCategoryName(), category.getCategoryName());
    }

    @Test
    void convertDtoToCategory(){
        CategoryDto dto = createCategoryDto(DEFAULT_ID, "Candy");

        Category category = assortmentConverters.convertDtoToCategory(dto);

        assertEquals(dto.getCategoryId(), category.getCategoryId());
        assertEquals(dto.getCategoryName(), category.getCategoryName());
    }

    @Test
    void convertProductToDto(){
        Category category = makeDefaultCategory();
        Product product = createProduct(45L, "beer test", 4.5, category, true);
        category.getProducts().add(product);

        ProductDto dto = assortmentConverters.convertProductToDto(product);

        assertEquals(dto.getProductId(), product.getProductId());
        assertEquals(dto.getProductName(), product.getProductName());
        assertEquals(dto.getProductPrice(), product.getProductPrice());
        assertEquals(dto.isProductOfAge(), product.isProductOfAge());
        assertEquals(dto.getCategoryDto().getCategoryId(), product.getCategory().getCategoryId());
        assertEquals(dto.getCategoryDto().getCategoryName(), product.getCategory().getCategoryName());
    }

    @Test
    void convertDtoToProduct(){
        String catName = "Soda";
        CategoryDto categoryDto = createCategoryDto(DEFAULT_ID, catName);
        Optional<Category> optionalCat = Optional.of(createCategory(DEFAULT_ID, catName));

        ProductDto dto = new ProductDto();
        dto.setProductId(789L);
        dto.setProductName("Cola");
        dto.setProductPrice(1.75);
        dto.setProductOfAge(false);
        dto.setCategoryDto(categoryDto);

        Product product = assortmentConverters.convertDtoToProduct(dto, optionalCat);

        assertEquals(dto.getProductId(), product.getProductId());
        assertEquals(dto.getProductName(), product.getProductName());
        assertEquals(dto.getProductPrice(), product.getProductPrice());
        assertEquals(dto.isProductOfAge(), product.isProductOfAge());
        assertEquals(dto.getCategoryDto().getCategoryId(), product.getCategory().getCategoryId());
        assertEquals(dto.getCategoryDto().getCategoryName(), product.getCategory().getCategoryName());
    }
}

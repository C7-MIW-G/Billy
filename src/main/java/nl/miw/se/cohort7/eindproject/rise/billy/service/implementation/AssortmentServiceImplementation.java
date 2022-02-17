package nl.miw.se.cohort7.eindproject.rise.billy.service.implementation;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.CategoryDto;
import nl.miw.se.cohort7.eindproject.rise.billy.model.Category;
import nl.miw.se.cohort7.eindproject.rise.billy.repository.CategoryRepository;
import nl.miw.se.cohort7.eindproject.rise.billy.repository.ProductRepository;
import nl.miw.se.cohort7.eindproject.rise.billy.service.AssortmentService;
import org.springframework.stereotype.Service;

import java.util.List;
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

    private CategoryDto convertToDto(Category category){
        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setCategoryId(category.getCategoryId());
        categoryDto.setCategoryName(category.getCategoryName());

        return categoryDto;
    }

    private Category convertToDatabaseObject(CategoryDto categoryDto){
        Category category = new Category();

        category.setCategoryId(categoryDto.getCategoryId());
        category.setCategoryName(categoryDto.getCategoryName());

        return category;
    }


    @Override
    public List<CategoryDto> findAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveCategory(CategoryDto categoryDto) {
        categoryRepository.save(convertToDatabaseObject(categoryDto));
    }
}

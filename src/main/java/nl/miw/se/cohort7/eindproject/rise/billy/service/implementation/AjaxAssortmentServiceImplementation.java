package nl.miw.se.cohort7.eindproject.rise.billy.service.implementation;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.MinProductAjaxDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.ProductDto;
import nl.miw.se.cohort7.eindproject.rise.billy.model.Category;
import nl.miw.se.cohort7.eindproject.rise.billy.model.Product;
import nl.miw.se.cohort7.eindproject.rise.billy.repository.CategoryRepository;
import nl.miw.se.cohort7.eindproject.rise.billy.service.AjaxAssortmentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * The service layer that connects the front- and back-end of the category and product objects for Ajax
 */
@Service
public class AjaxAssortmentServiceImplementation implements AjaxAssortmentService {

    private CategoryRepository categoryRepository;

    public AjaxAssortmentServiceImplementation(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private MinProductAjaxDto convertProductToDto(Product product){
        MinProductAjaxDto ajaxProduct = new MinProductAjaxDto();

        ajaxProduct.setId(product.getProductId());
        ajaxProduct.setName(product.getProductName());

        return ajaxProduct;
    }

    @Override
    public List<MinProductAjaxDto> findAllProductOfCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        List<MinProductAjaxDto> productList = new ArrayList<>();
        optionalCategory.ifPresent(
                category -> productList.addAll(category.getProducts()
                        .stream()
                        .map(this::convertProductToDto)
                        .collect(Collectors.toList())));
        return productList;
    }
}

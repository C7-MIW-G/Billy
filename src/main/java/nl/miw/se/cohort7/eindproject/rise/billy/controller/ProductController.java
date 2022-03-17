package nl.miw.se.cohort7.eindproject.rise.billy.controller;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.CategoryDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.ProductDto;
import nl.miw.se.cohort7.eindproject.rise.billy.service.AssortmentService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author Martijn Gäbler <m.gabler@st.hanze.nl>
 * Date created: 27/01/2022
 * Manage all product-related interactions.
 */

@Controller
@Secured("ROLE_BAR MANAGER")
@RequestMapping("/assortment/products")
public class ProductController {

    private AssortmentService assortmentService;

    public ProductController(AssortmentService assortmentService) {
        this.assortmentService = assortmentService;
    }

    @GetMapping("")
    protected String showProductOverview(Model model) {
        model.addAttribute("allProducts", assortmentService.findAllProducts());
        return "productOverview";
    }

    @GetMapping("/new/{categoryId}/")
    protected String showProductForm(@PathVariable("categoryId") Long categoryId, Model model){
        Optional<CategoryDto> optionalCategory = assortmentService.findCategoryById(categoryId);
        if (optionalCategory.isPresent()) {
            ProductDto product = new ProductDto();
            product.setCategoryDto(optionalCategory.get());
            model.addAttribute("product", product);
            return "productForm";
        }
        return "redirect:/assortment/categories/" + categoryId + "/";
    }

    @PostMapping("/save")
    protected String saveOrUpdateProduct(@Valid @ModelAttribute("product") ProductDto product, BindingResult result) {
        if (result.hasErrors()) {
            return "productForm";
        }
        assortmentService.saveProduct(product);
        return "redirect:/assortment/categories/" + product.getCategoryDto().getCategoryId() + "/";
    }

    @GetMapping("/update/{productId}/")
    protected String showProductFormUpdate(@PathVariable("productId") Long productId, Model model) {
        Optional<ProductDto> optionalProduct = assortmentService.findByProductId(productId);
        if (optionalProduct.isPresent()){
            model.addAttribute("product", optionalProduct.get());
            return "productForm";
        }
        return "redirect:/assortment";
    }

    @GetMapping("/details/{productId}/")
    protected String showProductDetails(@PathVariable("productId") Long productId, Model model) {
        Optional<ProductDto> optionalProduct = assortmentService.findByProductId(productId);
        if (optionalProduct.isPresent()){
            model.addAttribute("product", optionalProduct.get());
            return "productDetails";
        }
        return "redirect:/assortment";
    }

    @GetMapping("/delete/{productId}/")
    protected String deleteProduct(@PathVariable("productId") Long productId) {
        Optional<ProductDto> optionalProduct = assortmentService.findByProductId(productId);
        if (optionalProduct.isPresent()) {
            ProductDto product = optionalProduct.get();
            assortmentService.deleteProduct(product);
            return "redirect:/assortment/categories/" + product.getCategoryDto().getCategoryId() + "/";
        }
        return "redirect:/assortment";
    }
}
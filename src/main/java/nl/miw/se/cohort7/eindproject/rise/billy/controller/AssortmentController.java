package nl.miw.se.cohort7.eindproject.rise.billy.controller;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.CategoryDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.ProductDto;
import nl.miw.se.cohort7.eindproject.rise.billy.model.Product;
import nl.miw.se.cohort7.eindproject.rise.billy.service.AssortmentService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * Manage category and product related interactions
 */

@Controller
@Secured("ROLE_BAR MANAGER")
@RequestMapping("/assortment")
public class AssortmentController {

    private AssortmentService assortmentService;

    public AssortmentController(AssortmentService assortmentService) {
        this.assortmentService = assortmentService;
    }


    //categories
    @GetMapping({"", "/", "/categories"})
    protected String showCategoryOverview(Model model) {
        model.addAttribute("allCategories", assortmentService.findAllCategories());
        return "assortmentOverview";
    }

    @GetMapping("/categories/new")
    protected String showCategoryForm(Model model) {
        model.addAttribute("newCategory", new CategoryDto());
        model.addAttribute("headerText", "New Category");
        return "assortmentForm";
    }

    @PostMapping("/categories/new")
    protected String saveOrUpdateCategory(@Valid @ModelAttribute("newCategory") CategoryDto category,
                                         BindingResult result) {
        if (result.hasErrors()) {
            return "assortmentForm";
        }
        assortmentService.saveCategory(category);
        return "redirect:/assortment";
    }

    @GetMapping("/categories/{categoryId}")
    protected String showCategoryProductList(@PathVariable("categoryId") Long categoryId, Model model) {
        Optional<CategoryDto> optionalCategory = assortmentService.findCategoryById(categoryId);
        if (optionalCategory.isPresent()){
            CategoryDto category = optionalCategory.get();
            List<ProductDto> products = assortmentService.findAllProductOfCategory(category.getCategoryId());
            model.addAttribute("category", category);
            model.addAttribute("productList", products);
            return "categoryProductOverview";
        }
        return "redirect:/assortment";
    }


    //products
    @GetMapping("/products")
    protected String showProductOverview(Model model) {
        model.addAttribute("allProducts", assortmentService.findAllProducts());
        return "productOverview";
    }

    @GetMapping("/products/new/{categoryId}")
    protected String showProductForm(@PathVariable("categoryId") Long categoryId, Model model){
        Optional<CategoryDto> optionalCategory = assortmentService.findCategoryById(categoryId);
        if (optionalCategory.isPresent()) {
            ProductDto product = new ProductDto();
            product.setCategoryDto(optionalCategory.get());
            model.addAttribute("product", product);
            return "productForm";
        }
        return "redirect:/assortment/categories/" + categoryId;
    }

    @PostMapping("/products/save")
    protected String saveOrUpdateProduct(@Valid @ModelAttribute("product") ProductDto product, BindingResult result) {
        if (result.hasErrors()) {
            return "productForm";
        }
        assortmentService.saveProduct(product);
        return "redirect:/assortment/categories/" + product.getCategoryDto().getCategoryId();
    }

    @GetMapping("/products/update/{productId}")
    protected String showProductFormUpdate(@PathVariable("productId") Long productId, Model model) {
        Optional<ProductDto> optionalProduct = assortmentService.findByProductId(productId);
        if (optionalProduct.isPresent()){
            model.addAttribute("product", optionalProduct.get());
            return "productForm";
        }
        return "redirect:/assortment";
    }

    @GetMapping("/products/details/{productId}")
    protected String showProductDetails(@PathVariable("productId") Long productId, Model model) {
        Optional<ProductDto> optionalProduct = assortmentService.findByProductId(productId);
        if (optionalProduct.isPresent()){
            model.addAttribute("product", optionalProduct.get());
            return "productDetails";
        }
        return "redirect:/assortment";
    }

    @GetMapping("/products/delete/{productId}")
    protected String deleteProduct(@PathVariable("productId") Long productId) {
        Optional<ProductDto> optionalProduct = assortmentService.findByProductId(productId);
        if (optionalProduct.isPresent()) {
            ProductDto product = optionalProduct.get();
            assortmentService.deleteProduct(product);
            return "redirect:/assortment/categories/" + product.getCategoryDto().getCategoryId();
        }
        return "redirect:/assortment";
    }
}

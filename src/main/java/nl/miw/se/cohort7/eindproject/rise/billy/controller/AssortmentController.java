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
        model.addAttribute("category", new CategoryDto());
        return "assortmentForm";
    }

    @PostMapping("/categories/new")
    protected String saveOrUpdateCategory(@Valid @ModelAttribute("category") CategoryDto category,
                                         BindingResult result) {
        if (result.hasErrors()) {
            return "assortmentForm";
        }
        assortmentService.saveCategory(category);
        return "redirect:/assortment/categories/" + category.getCategoryId();
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

    @GetMapping("/categories/delete/{categoryId}")
    protected String deleteCategory(@PathVariable("categoryId") Long categoryId){
        Optional<CategoryDto> optionalCategory = assortmentService.findCategoryById(categoryId);
        optionalCategory.ifPresent(categoryDto -> assortmentService.deleteCategory(categoryDto));
        return "redirect:/assortment";
    }

    @GetMapping("/categories/update/{categoryId}")
    protected String deleteCategory(@PathVariable("categoryId") Long categoryId, Model model) {
        Optional<CategoryDto> optionalCategory = assortmentService.findCategoryById(categoryId);
        if (optionalCategory.isPresent()){
            model.addAttribute("category", optionalCategory.get());
            return "assortmentForm";
        }
        return "redirect:/assortment";
    }
}
